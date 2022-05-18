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
import {$do, coreActions, historyObj} from '@tesler-ui/core'
import {Observable} from 'rxjs/Observable'
import {getBasicAuthRequest, logout} from 'api/session'
import {AxiosError} from 'axios'
import {TeslerLoginResponse} from 'interfaces/session'
import {CustomEpic} from 'interfaces/actions'

const responseStatusMessages: Record<number, string> = {
    401: 'Unauthorized',
    403: 'Access denied'
}

const loginEpic: CustomEpic = (action$, store) => action$.ofType(coreActions.login)
    .switchMap((action) => {
        const login = action.payload && action.payload.login
        const password = action.payload && action.payload.password
        return getBasicAuthRequest(login, password)
            .mergeMap((data) => {
                const typedResponse = data as unknown as TeslerLoginResponse
                if (!data.redirectUrl) {
                    sessionStorage.setItem('session', JSON.stringify({
                        fullName: typedResponse.fullName,
                        login: typedResponse.login
                    }))
                }

                return Observable.of($do.loginDone({
                    screens: typedResponse.screens,
                    fullName: typedResponse.fullName,
                    login: typedResponse.login
                } as TeslerLoginResponse))
            })
            .catch((error: AxiosError) => {
                const errorMsg = (error.response)
                    ? responseStatusMessages[error.response.status] || 'Server application unavailable'
                    : 'Empty response from server'
                return Observable.of($do.loginFail({errorMsg}))
            })
    })

const logoutEpic: CustomEpic = (action$, store) =>
    action$.ofType(coreActions.logout)
        .switchMap((action) => logout().map(() => {
            const history = historyObj
            history.action = 'PUSH'
            history.push('')
            return $do.logoutDone(null)
        }))

const logoutDoneEpic: CustomEpic = (action$, store) =>
    action$.ofType(coreActions.logoutDone)
        .mergeMap((action) => {
            sessionStorage.removeItem('session')
            return Observable.empty()
        })

export const sessionEpics = {
    loginEpic, logoutEpic, logoutDoneEpic
}
