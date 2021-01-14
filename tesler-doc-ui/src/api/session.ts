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
//import { DataItem } from '@tesler-ui/core/interfaces/data'

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
    },
    transformResponse: (res, req) => {
        if (res?.data?.[0] && !Object.keys(res?.data?.[0]).length) {
            // res.data = getTreeMock()
        }
        return res
    }
})


function getTreeMock() {
    /*const result: DataItem[] = []
    result.push({
        id: `99`,
        parentId: '0',
        name: `Test`,
        vstamp: 0,
        level: 0
    })
    for (let j = 0; j < 5; j++) {
        for (let i = 0; i < 10000; i++) {
            result.push({
                id: `${j}-${i}`,
                parentId: i === 0 ? '0' : `${j}-0`,
                name: `Test ${j}-${i}`,
                vstamp: 0,
                level: i === 0 ? 0 : 1
            })
        }
    }

    return result*/
    const tree = [
        {
            id: `99`,
            parentId: '0',
            name: `Test`,
            vstamp: 0,
            level: 0
        },
        { id: '1', name: 'one', parentId: '0' },
        { id: '2', name: 'two', parentId: '0' },
        { id: '3', name: 'three', parentId: '0' },
        { id: '11', name: 'four', parentId: '1' },
        { id: '12', name: 'five', parentId: '1' },
        { id: '13', name: 'six', parentId: '1' },
        { id: '31', name: 'seven', parentId: '3' },
        { id: '32', name: 'lucky Eight', parentId: '3' },
        { id: '21', name: 'nine', parentId: '2' },
        { id: '22', name: 'ten', parentId: '2' },
        { id: '221', name: 'eleven', parentId: '22' },
        { id: '2211', name: 'Lucky Twelve', parentId: '221' },
    ]
    
    return tree
}

