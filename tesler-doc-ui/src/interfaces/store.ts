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

import {LoginResponse} from '@tesler-ui/core/interfaces/session'
import * as util from '@tesler-ui/core/actions/actions-utils'
import {ActionPayloadTypes} from '@tesler-ui/core/actions/actions'
import {AppState} from 'reducers'

export interface TeslerLoginResponse extends LoginResponse {
    fullName: string,
    login: string
}

class TeslerActionPayloadTypes extends ActionPayloadTypes {
    loginDone: TeslerLoginResponse
}

export declare type ActionsMap = util.uActionsMap<TeslerActionPayloadTypes>
export declare type AnyAction = util.AnyOfMap<ActionsMap> | {
    type: ' UNKNOWN ACTION ';
}

export declare type TeslerCoreReducer<ReducerState, ClientActions, State = AppState> =
    (state: ReducerState, action: AnyAction & ClientActions, store?: Readonly<State>) => ReducerState

export interface TeslerClientReducer<ReducerState, ClientActions> {
    initialState: ReducerState
    override?: boolean
    reducer: TeslerCoreReducer<ReducerState, ClientActions>
}

export declare type TeslerClientReducersMapObject<ClientStore, ClientActions> = {
    [reducerName in keyof ClientStore]: TeslerClientReducer<ClientStore[keyof ClientStore], ClientActions>;
}
