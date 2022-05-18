import {matchOperationRole, sendOperationEpicImpl, customAction, buildBcUrl} from '@tesler-ui/core'
import {CustomEpic, actionTypes} from '../interfaces/actions'
import {$do} from '../actions/types'
import {Observable} from 'rxjs/Observable'
import { CustomEpicSlice } from '@tesler-ui/core/interfaces/customEpics'

const sendOperation: CustomEpic = (action$, store) => action$
.ofType(actionTypes.sendOperation)
.filter(action => {
    const isCustom = matchOperationRole('none', action.payload, store.getState())
    const hasOwnEpic = matchOperationRole('bulk-update', action.payload, store.getState())
        || matchOperationRole('bulk-delete', action.payload, store.getState())
    return isCustom && !hasOwnEpic
})
.mergeMap(action => sendOperationEpicImpl(action, store))

const bulkDelete: CustomEpic = (action$, store) => action$
.ofType(actionTypes.sendOperation)
.filter(action => matchOperationRole('bulk-delete', action.payload, store.getState()))
.mergeMap(action => {
    const state = store.getState()
    const bcName = state.view.widgets.find(item => item.name === action.payload.widgetName)?.bcName
    const data = {
        bulkIds: state.view.selectedItems
    }

    const bcUrl = buildBcUrl(bcName, true)
    return customAction(state.screen.screenName, bcUrl, data, null, { _action: 'bulk-delete' })
    .mergeMap(response => {
        return Observable.concat(
            Observable.of($do.bcCancelPendingChanges({ bcNames: [bcName] })),
            Observable.of($do.sendOperationSuccess({ bcName, cursor: null })),
            Observable.of($do.bcForceUpdate({ bcName }))
        )
    })
})

const bulkUpdate: CustomEpic = (action$, store) => action$
.ofType(actionTypes.sendOperation)
.filter(action => matchOperationRole('bulk-update', action.payload, store.getState()))
.mergeMap(action => {
    const state = store.getState()
    const bcName = state.view.widgets.find(item => item.name === action.payload.widgetName)?.bcName
    const pendingChanges = state.view.pendingDataChanges[bcName]
        && Object.values(state.view.pendingDataChanges[bcName])?.[0]
    const data = {
        ...pendingChanges,
        bulkIds: state.view.selectedItems,
    }

    const bcUrl = buildBcUrl(bcName, true)
    return customAction(state.screen.screenName, bcUrl, data, null, { _action: 'bulk-update' })
    .mergeMap(response => {
        return Observable.concat(
            Observable.of($do.bcCancelPendingChanges({ bcNames: [bcName] })),
            Observable.of($do.sendOperationSuccess({ bcName, cursor: null })),
            Observable.of($do.bcForceUpdate({ bcName }))
        )
    })
})

export const viewEpics: CustomEpicSlice<'viewEpics'> = {
    sendOperation,
    bulkDelete,
    bulkUpdate
}
