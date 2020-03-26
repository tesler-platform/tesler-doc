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

import {INCREMENT_BUTTON, DECREMENT_BUTTON} from 'actions/actions'
import {coreActions} from '@tesler-ui/core'

export interface ITestState {
    clicks: number
}

export const initialState = {
    clicks: 0
}

export default function testReducer(state: ITestState = { clicks: 0 }, action: any ): ITestState {
    switch (action.type) {
        case coreActions.changeLocation:
            return { ...state, clicks: state.clicks + 1 }
        case INCREMENT_BUTTON:
            return { ...state, clicks: state.clicks + 1 }
        case DECREMENT_BUTTON:
            return { ...state, clicks: state.clicks - 1 }
        default:
            return state
    }
}
