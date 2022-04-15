Now let's see what is under the hood. The code relies on the assumption that your Tesler UI application was build
from our [CRA template](https://github.com/tesler-platform/cra-template-typescript) is generic enough to be
implemented in any project.

## Step 1: Store records selection

We'll be tracking currently selected records in the `view` slice of the redux store:

```tsx
export interface TeslerViewState extends ViewState {
    // ...
    selectedItems: string[]
}
```

We'll also need some actions and reducers:

#### src/actions/types.ts
```typescript
export class CustomActionTypes extends TeslerActionPayloadTypes {
    /**
     * Toggle selection for specified records
     */
    selectRecord: {
        /**
         * Ids of toggled records
         */
        ids: string[],
        /**
         * Selected or not
         */
        selected: boolean
    } = z
}
```

#### src/reducers/view.ts
```typescript
case actionTypes.selectRecord: {
    const selectedItems = action.payload.selected
        ? [ ...state.selectedItems, ...action.payload.ids ]
        : state.selectedItems.filter(item => !action.payload.ids.includes(item))
    return {
        ...state,
        selectedItems
    }
}
case actionTypes.bcFetchDataSuccess: {
    return {
        ...state,
        selectedItems: []
    }
}
```

So far no surprises: we've introduced a pretty unremarkable `selectRecord` *action* which we will be calling to toggle
our selected records.
`selecteRecord` *reducer* also pretty usual. You may wonder why have we also introduced `bcFetchDataSuccess` reducer:
this action is called when our records refetched, and when records refetched we generally want to drop the state
of selected records.

One last reducer that we'll need is a reducer to clear current *cursor* when we perform bulk operations. Generally when
we work with some widget we have only one currently active record (we call id of such record *the cursor*), but it is
not applicable when we initiate bulk operation as it's by definition applied to several records.  
There are several ways to handle this, but for simplicity in this tutorial we'll solve it with trivial though verbose 
reducer:

#### src/reducers/screen.ts
```ts
case coreActions.sendOperation: {
    return ['bulk-delete', 'bulk-update'].includes(action.payload.operationType) 
        ? {
            ...state,
            bo: {
                ...state.bo,
                bc: {
                    ...state.bo.bc,
                    [action.payload.bcName]: {
                        ...state.bo.bc[action.payload.bcName],
                        cursor: null
                    }
                }
            }
        }
        : state
}
```

As you can see, it fires for `sendOperation` actions when it matches the type of our bulk operation and then clears
cursor for business component of our widget.

Now let's customize `<TableWidget />` to call our `selectRecord` action.

## Step 2: Customize `<TableWidget />` 

By default `<TableWidget />` does not provide selection functionality but it can be enabled via `rowSelection`
configuration property.   
So, what we need is a higher-order component (HOC) to wrap the `<TableWidget />` with appropriate `rowSelection`.
We also probably wouldn't want *every* table widget to have this functionality, so we make it configurable through
`selectable` property in widget options.  

Let's take a look on simple example of such HOC:

#### src/components/widgets/TableWidget/BulkTableWidget.tsx
```tsx
import React from 'react'
import {useDispatch, useSelector} from 'react-redux'
import {TableWidget} from '@tesler-ui/core'
import {TableWidgetProps} from '@tesler-ui/core/components/widgets/TableWidget/TableWidget'
import {$do} from '../../../actions/types'
import {AppState} from '../../../interfaces/storeSlices'
import {DataItem} from '@tesler-ui/core/interfaces/data'
import {WidgetOptions} from '@tesler-ui/core/interfaces/widget'

// New `selectable` flag for widget meta description
type CustomWidgetOptions = WidgetOptions & { selectable: true }

// Our HOC will receive the same props as a regular `<TableWidget />`
export const BulkTableWidget: React.FC<TableWidgetProps> = (props) => {
    // Get currently selected items from the store
    const selectedItems = useSelector((state: AppState) => state.view.selectedItems)
    const dispatch = useDispatch()

    const rowReselection = React.useMemo(() => {
        // If widget is not `selectable` then return undefined
        if (!(props.meta.options as CustomWidgetOptions)?.selectable) {
            return
        }
        return {
            // This will control checked records
            selectedRowKeys: selectedItems,
            // This callback will be called when the record is checked
            onSelect: (record: DataItem, selected: boolean) => {
                // Dispatch an action with id of selected record
                dispatch($do.selectRecord({ ids: [record.id], selected }))
            },
            onSelectAll: (selected: boolean, selectedRows: DataItem[], changedRows: DataItem[]) => {
                // Dispatch the same action for `select all` checkbox
                dispatch($do.selectRecord({ ids: changedRows.map(item => item.id), selected }))
            }
        }
    }, [selectedItems, props.meta.options])

    return <TableWidget
        {...props}
        rowSelection={rowReselection}
    />
}

export default React.memo(BulkTableWidget)

```

For our application to understand that `<BulkTableWidget />` component should be rendered instead of
 `<TableWidget />` we need to map widget type to component:
 
#### src/AppLayout.tsx
 ```tsx
import {CustomWidget} from '@tesler-ui/core/interfaces/widget'
import BulkTableWidget from 'components/widgets/TableWidget/BulkTableWidget'

const customWidgets: Record<string, CustomWidget> = {
    [WidgetTypes.List]: TableWidget,
}

export function Layout(props: LayoutProps) {
    // ...
    return (
        // ...
        <View
            customWidgets={customWidgets}
        />
    )
}
```

Now we have one more step before we move on to backend.

## Step 3: Epics for `bulk-delete` and  `bulk-update`

As `bulk-delete` and `bulk-update` initiate AJAX requests to Tesler API, we'll handle them with epics. You can [read 
more about epics](#/screen/tutorial/view/epics-overview) on their dedicated page, but in short they are RxJS-based
 alternative to redux-thunk and redux-saga to handle asynchronous jobs (like network requests).  
 
We'll need three epics. First, we should let now our built-in `sendOperation` epic that it shouldn't fire for
`bulk-delete` and `bulk-update` operation types as we handle them with our own epics. 
Then of course we write epics for `bulk-delete` and `bulk-update` actions.

#### src/epics/view.ts
```ts
import {matchOperationRole, sendOperationEpicImpl, customAction, buildBcUrl} from '@tesler-ui/core'
import {CustomEpic, actionTypes} from '../interfaces/actions'
import {$do} from '../actions/types'
import {Observable} from 'rxjs/Observable'

const sendOperation: CustomEpic = (action$, store) => action$
.ofType(actionTypes.sendOperation)
.filter(action => {
    // We'll recreate our internal check that separates `sendOperation` handlers for built-in operations and custom ones
    const isCustom = matchOperationRole('none', action.payload, store.getState())
    // Here we'll exclude operation types that we want to handle in our own epics
    const hasOwnEpic = matchOperationRole('bulk-update', action.payload, store.getState())
        || matchOperationRole('bulk-delete', action.payload, store.getState())
    return isCustom && !hasOwnEpic
})
// We'll reuse default implementation for non-bulk custom operations
.mergeMap(action => sendOperationEpicImpl(action, store))

export const viewEpics: CustomEpicSlice<'viewEpics'> = {
    // Here we override built-in epic for `sendOperation` with our own implementation
    sendOperation,
}
```

This will just make sure that built-in handler for `sendOperation` action won't fire for our bulk actions.  
Now let's write an epic for `bulk-delete` operation:

#### src/epics/view.ts
```ts
const bulkDelete: CustomEpic = (action$, store) => action$
.ofType(actionTypes.sendOperation)
.filter(action => matchOperationRole('bulk-delete', action.payload, store.getState()))
.mergeMap(action => {
    const state = store.getState()
    // Get the business component name of the widget that initiated request
    const bcName = state.view.widgets.find(item => item.name === action.payload.widgetName)?.bcName
    // Get the ids of affected records from the store
    const data = {
        bulkIds: state.view.selectedItems
    }
    // Tesler API services are aware of the widgets hierarchy so use this helper to build the url to the endpoint
    // of this hierarchy
    const bcUrl = buildBcUrl(bcName, true)
    // We send POST request with ids of affected records
    return customAction(state.screen.screenName, bcUrl, data, null, { _action: 'bulk-delete' })
    .mergeMap(response => {
        return Observable.concat(
            // drop any pending changes: they are no longer valid after we removed records
            Observable.of($do.bcCancelPendingChanges({ bcNames: [bcName] })),
            // confirm succeful request completion
            Observable.of($do.sendOperationSuccess({ bcName, cursor: null })),
            // refresh data to receive actual state
            Observable.of($do.bcForceUpdate({ bcName }))
        )
    })
})

export const viewEpics: CustomEpicSlice<'viewEpics'> = {
    // Here we override built-in epic for `sendOperation` with our own implementation
    sendOperation,
    // We also export our own epics
    bulkDelete
}
```

Essentially this will send a POST request to `api/v1/custom-action/tutorial/bulkUpdateExample?_action=bulk-update`
endpoint and Tesler API will pick up from there.  
But before we moved there let's finish with `bulk-update` epic. It's pretty much the same as `bulk-delete', so we'll
just comment new lines:

#### src/epics/view.ts
```ts
const bulkUpdate: CustomEpic = (action$, store) => action$
.ofType(actionTypes.sendOperation)
// change argument here
.filter(action => matchOperationRole('bulk-update', action.payload, store.getState()))
.mergeMap(action => {
    const state = store.getState()
    const bcName = state.view.widgets.find(item => item.name === action.payload.widgetName)?.bcName
    // Get pending changes that we should apply to all selected records
    const pendingChanges = state.view.pendingDataChanges[bcName] && Object.values(state.view.pendingDataChanges[bcName])?.[0]
    const data = {
        ...pendingChanges,
        bulkIds: state.view.selectedItems,
    }
    const bcUrl = buildBcUrl(bcName, true)
    // and change `_action` here
    return customAction(state.screen.screenName, bcUrl, data, null, { _action: 'bulk-update' })
    .mergeMap(response => {
        return Observable.concat(
            Observable.of($do.bcCancelPendingChanges({ bcNames: [bcName] })),
            Observable.of($do.sendOperationSuccess({ bcName, cursor: null })),
            Observable.of($do.bcForceUpdate({ bcName }))
        )
    })
})
```

Now our Tesler UI application is all set.
So how our application handles this on Tesler API side?

## Step 4: Setting up the service

Suppose our `bulkUpdateExample` business component is handled by `BulkUpdateExampleService`. Let's add bulk actions to
our service:

```java
public class BulkUpdateExampleServiceImpl
    extends VersionAwareResponseService<BulkUpdateExampleDTO, BulkUpdateExample>
    implements BulkUpdateExampleService {
        
    @Override
    public Actions<BulkUpdateExampleDTO> getActions() {
        return Actions.<BulkUpdateExampleDTO>builder()
                .action("bulk-update", "Update").invoker(this::bulkUpdate)
                    .scope(ActionScope.BC)
                    .withIcon(TeslerActionIconSpecifier.SAVE, false)
                    .add()
                .action("bulk-delete", "Delete").invoker(this::bulkDelete)
                    .scope(ActionScope.BC)
                    .withIcon(TeslerActionIconSpecifier.DELETE, false)
                    .add()
                .build();
    }
}
```

Here we simply configure two buttons. More interesting is of course are methods that will be invoked by those buttons:

```java
private ActionResultDTO<BulkUpdateExampleDTO> bulkDelete(BusinessComponent bc, BulkUpdateExampleDTO data) {
		Optional.ofNullable(data.getBulkIds()).ifPresent(res -> {
			res.forEach(item -> {
				BulkUpdateExample entity = baseDAO.findById(BulkUpdateExample.class, Long.parseLong(item));
				baseDAO.delete(entity);
			});
		});
		return new ActionResultDTO<BulkUpdateExampleDTO>();
	}

private ActionResultDTO<BulkUpdateExampleDTO> bulkUpdate(BusinessComponent bc, BulkUpdateExampleDTO data) {
    Optional.ofNullable(data.getBulkIds()).ifPresent(res -> {
        res.forEach(item -> {
            BulkUpdateExample entity = baseDAO.findById(BulkUpdateExample.class, Long.parseLong(item));
            entity.setName(data.getName());
            entity.setDescription(data.getDescription());
            baseDAO.save(entity);
        });
    });
    return new ActionResultDTO<BulkUpdateExampleDTO>();
}
```

As you can see the handlers are pretty much the same as desribed in our
[Features section for Bulk Actions](#/screen/components/features/bulk-actions/): the ids of affected records are
received from the body of the request, and then pretty much any logic can be implemented to handle them appropriately.  
This example handles them in a simplistic way of iterating each id, finding matching record and calling DAO method over
 this record, so in real service you might want to take advantage of the bulk insert/delete operation of your database.
 