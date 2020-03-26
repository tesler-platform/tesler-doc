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

import {axiosGet, buildUrl} from '@tesler-ui/core'
import {LoginResponse} from '@tesler-ui/core/interfaces/session'
import axios from 'axios'

const __API__ = '/api/v1/'
const __AJAX_TIMEOUT__ = 900000
const __CLIENT_ID__: number = Date.now()

export const HEADERS = { 'Pragma': 'no-cache', 'Cache-Control': 'no-cache, no-store, must-revalidate' }

export function getBasicAuthRequest(login?: string, password?: string) {
    const hash = login && new Buffer(`${login}:${password}`).toString('base64')
    const tzOffset = -(new Date()).getTimezoneOffset() * 60
    const entrypointUrl = `/${window.location.hash}`
    return axiosGet<LoginResponse>(
        buildUrl`login?_tzoffset=${tzOffset}&_entrypointUrl=${entrypointUrl}`,
        (hash) ? { headers: { Authorization: `Basic ${hash}` } } : {}
    )
}

export function logout() {
    return axiosGet(buildUrl`logout`)
}

export const axiosInstance = axios.create({
    baseURL: __API__,
    timeout: __AJAX_TIMEOUT__,
    responseType: 'json',
    headers: {
        ...HEADERS,
        ...{ClientId : __CLIENT_ID__},
    }
})
