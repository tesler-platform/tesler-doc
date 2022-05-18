/*
 * TESLERDOC - UI
 * Copyright (C) 2020 Tesler Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import {coreActions} from '@tesler-ui/core'
import {AppState} from 'interfaces/storeSlices'
import {actionTypes, AnyAction} from 'interfaces/actions'
import {TeslerViewState} from 'interfaces/view'

export const initialState: TeslerViewState = {
    id: null,
    name: null,
    url: null,
    widgets: [],
    columns: null,
    readOnly: false,
    rowHeight: null,
    rowMeta: {},
    metaInProgress: {},
    popupData: { bcName: null },
    pendingDataChanges: {},
    handledForceActive: {},
    systemNotifications: [],
    selectedItems: []
}

export default function screenReducer(
    state: TeslerViewState = initialState,
    action: AnyAction,
    store: Readonly<AppState>
): TeslerViewState {
    switch (action.type) {
        case coreActions.logoutDone:
            return initialState
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
        case coreActions.selectTableCell: {
            return state.selectedItems.includes(action.payload.rowId)
                ? state
                : {
                    ...state,
                    selectedItems: [ ...state.selectedItems, action.payload.rowId]
                }
        }
        case coreActions.sendOperationSuccess: {
            return { ...state, pendingDataChanges: initialState.pendingDataChanges }
        }
        default:
            return state
    }
}
