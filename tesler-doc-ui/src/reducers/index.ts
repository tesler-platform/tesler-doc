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
import screenReducer, {initialState as screenInitialState} from './screen'
import dataReducer, {initialState as dataInitialState} from './data'
import viewReducer, {initialState as viewInitialState} from './view'
import sessionReducer, {initialState as sessionInitialState, getSessionAuth} from './session'
import {AppReducers} from '../interfaces/storeSlices'
import {RootReducer} from 'interfaces/store'

const sessionInitialWithAuth = getSessionAuth()

export const reducers: RootReducer<AppReducers, any> = {

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
