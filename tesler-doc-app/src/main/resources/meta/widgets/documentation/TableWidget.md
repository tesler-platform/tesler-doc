## Front-end implementation (TableWidget component)

TableWidget is wrapper of `antd` `Table`.  
It provides specific Tesler logic.  
Overrides some `antd` `Table` methods (see table below for details). Provides ability to pass all `antd` `Table` properties directly to `Table` (Available since @tesler-ui/core 1.16.0).

## API

### TableWidget properties

|  Property |  Description | Type  |
|:---|:---|:---|
| **columnTitleComponent**  | Custom implementation of column title component | `Function(options?:{widgetName, widgetMeta, rowMeta}): ReactNode` |
| **meta***  | Meta data of widget `List`  | `WidgetTableMeta`  |
| **rowSelection**  | Overrides `rowSelection` of antd [config](https://3x.ant.design/components/table/#rowSelection)  | `TableRowSelection<DataItem>` |
| **showRowActions**  | Shows row actions  | `boolean`  |
| **allowEdit**  | Allows edit data  | `boolean`  |
| **paginationMode**  | Toggles pagination mode   | `PaginationMode`  |
| **disablePagination**  | Disables pagination  | `boolean`  |
| **disableDots**  | Disables column which contains row menus | `boolean`  |
| **controlColumns**  | Custom control columns  | ```Array<{column: ColumnProps<DataItem>, position: 'left' or 'right'}>```  |

### columnTitleComponent properties
Available since @tesler-ui/core 1.16.0

|  Property |  Description | Type  |
|:---|:---|:---|
| **widgetName***  | Widget name which takens from **meta** | `string` |
| **widgetMeta***  | Items of **meta.fields** array | `WidgetListField`  |
| **rowMeta***  | Item of row meta for specific column | `RowMetaField` |

### controlColumns properties
Available since @tesler-ui/core 1.14.0

|  Property |  Description | Type  |
|:---|:---|:---|
| **column***  | Additional customization column on the client application | `ColumnProps<DataItem>` |
| **position***  | Position of customization column | `'left' or 'right'`  |

### Overriding of Tesler's TableWidget

For purposes of extending TableWidget logic you can override it in your project.

For example:
`your-ui-project/src/components/widgets/TableWidget/TableWidget.tsx`
```typescript
import {TableWidget as CoreTableWidget} from '@tesler-ui/core'
import {WidgetTableMeta, WidgetListField} from '@tesler-ui/core/interfaces/widget'
import {RowMetaField} from '@tesler-ui/core/interfaces/rowMeta'
///...
interface TableWidgetProps {
    meta: WidgetTableMeta,
  // Some your properties. You can extend it by {TableWidgetProps} from '@tesler-ui/core/components/widgets/TableWidget/TableWidget'
 
}
function TableWidget(props: TableWidgetProps) {
    // You can provide your logic here
    
    // Example of antd Table props usage
    // If we should expand some of Table rows
    const expandRender = // to define render for expanded rows
    
    // Example of `columnTitleComponent` prop usage
    // You can define `YourColumnTitle` component
    const renderColumnTitle = React.useCallback(
            (pr: {widgetName: string, widgetMeta: WidgetListField, rowMeta: RowMetaField}) =>
                <YourColumnTitle
                    widgetName={pr.widgetName}
                    widgetMeta={pr.widgetMeta}
                    rowMeta={pr.rowMeta}
                />,
            []
        )
    // Example of `controlColumns` prop usage
    // You can define `YourColumn` component
    const yourColumn: ColumnProps<DataItem> = {
            title: '',
            key: '_yourColumn',
            width: '10px',
            render: (text, dataItem): React.ReactNode => {
                return <YourColumn
                    yourProps={props.someProp}
                    ...
                />
            }
        }
    
    return <CoreTableWidget
            {...props}
            expandedRowRender={expandRender} // Example of antd Table props usage
            columnTitleComponent={renderColumnTitle} // Example of `columnTitleComponent` prop usage
            controlColumns={[{column: yourColumn, position: 'left'}]} // Example of `controlColumns` prop usage
        />
}
```
Or check [live example](https://github.com/tesler-platform/tesler-doc/blob/develop/tesler-doc-ui/src/components/widgets/TableWidget/TableWidget.tsx) out.  
**NB** Do not forget to pass your `TableWidget` implementation to `customWidgets` list of `View` properties.
