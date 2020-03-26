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

import testReducer, {ITestState, initialState as testReducerInitial} from 'reducers/testReducer'
import screenReducer, {initialState as screenInitialState} from 'reducers/screen'
import dataReducer, {initialState as dataInitialState} from 'reducers/data'
import viewReducer, {initialState as viewInitialState} from 'reducers/view'
import sessionReducer, {getSessionAuth, initialState as sessionInitialState, TeslerSessionState} from 'reducers/session'
import {Store} from '@tesler-ui/core/interfaces/store'
import {TeslerScreenState} from 'interfaces/screen'
import {TeslerClientReducersMapObject} from 'interfaces/store'

const sessionInitialWithAuth = getSessionAuth()

export const reducers: TeslerClientReducersMapObject<AppReducers, any> = {
    testReducer: {
        initialState: testReducerInitial,
        reducer: testReducer
    },
    screen: {
        initialState: screenInitialState,
        reducer: screenReducer
    },
    data: {
        initialState: dataInitialState,
        reducer: dataReducer
    },
    view: {
        initialState: viewInitialState,
        reducer: viewReducer
    },
    session: {
        initialState: (sessionInitialWithAuth) ? sessionInitialWithAuth : sessionInitialState,
        reducer: sessionReducer
    }
}

export interface AppReducers extends Partial<Store> {
    testReducer: ITestState,
    screen: TeslerScreenState,
    session: TeslerSessionState
}

export type AppState = Store & AppReducers
