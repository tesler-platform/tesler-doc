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
import {Session} from '@tesler-ui/core/interfaces/session'
import {AppState} from 'interfaces/storeSlices'
import {actionTypes, AnyAction} from 'interfaces/actions'
import {TeslerLoginResponse} from 'interfaces/session'

export interface TeslerSessionState extends Session {
    savedSessionActive: boolean,
    fullName: string,
    login: string
}

export const initialState: TeslerSessionState = {
    active: true,
    savedSessionActive: false,
    loginSpin: false,
    screens: [],
    fullName: '',
    login: ''
}

export function getSessionAuth(): TeslerSessionState {
    const rawAuth = sessionStorage.getItem('session')
    return (rawAuth)
        ? {
            ...initialState,
            ...JSON.parse(rawAuth),
            savedSessionActive: true,
        }
        : null
}

export default function sessionReducer(
    state: TeslerSessionState = initialState,
    action: AnyAction,
    store: Readonly<AppState>
): TeslerSessionState {
    switch (action.type) {
        case actionTypes.loginDone: {
            return {
                ...state,
                fullName: (action.payload as TeslerLoginResponse).fullName,
                login: (action.payload as TeslerLoginResponse).login,
                savedSessionActive: false
            }
        }
        case coreActions.logout: {
            return {...state, loginSpin: false, active: false}
        }
        case coreActions.logoutDone: {
            return {...state, loginSpin: false, active: false, screens: []}
        }
        case coreActions.loginFail: {
            return (state.savedSessionActive)
                ? {...state, savedSessionActive: false}
                : state
        }
        default:
            return state
    }
}
