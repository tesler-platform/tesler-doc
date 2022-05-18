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

import {AnyAction} from '@tesler-ui/core/actions/actions'
import {DataState} from '@tesler-ui/core/interfaces/data'
import {coreActions} from '@tesler-ui/core'
import {AppState} from 'interfaces/storeSlices'

export const initialState: DataState = {
}

export default function screenReducer(
    state: DataState = initialState,
    action: AnyAction,
    store: Readonly<AppState>
): DataState {
    switch (action.type) {
        case coreActions.logoutDone:
            return initialState
        default:
            return state
    }
}
