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
import {BcMetaState} from '@tesler-ui/core/interfaces/bc'
import {ObjectMap} from '@tesler-ui/core/interfaces/objectMap'
import {TeslerScreenState} from 'interfaces/screen'
import {actionTypes, AnyAction} from 'interfaces/actions'
import {AppState} from 'interfaces/storeSlices'

export const initialState: TeslerScreenState = {
    screenName: '',
    bo: {
        activeBcName: null,
        bc: {} as ObjectMap<BcMetaState>
    },
    cachedBc: {},
    views: [],
    primaryView: '',
    filters: {},
    sorters: {},
    menuVisible: true,
    mobileMenu: false
}

export default function screenReducer(
    state: TeslerScreenState = initialState,
    action: AnyAction,
    store: Readonly<AppState>
): TeslerScreenState {
    switch (action.type) {
        case coreActions.logoutDone:
            return initialState
        case actionTypes.setMenuVisible: {
            return {
                ...state,
                menuVisible: action.payload
            }
        }
        case actionTypes.setMobileMenu: {
            return {
                ...state,
                mobileMenu: action.payload
            }
        }
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
        default:
            return state
    }
}
