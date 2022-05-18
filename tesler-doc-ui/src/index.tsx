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

import 'imports/shim'
import React from 'react'
import {render} from 'react-dom'
import {Provider} from '@tesler-ui/core'
import {reducers} from 'reducers'
import {epics} from 'epics'
import Layout from 'components/AppLayout/AppLayout'
import {axiosInstance} from 'api/session'
import './antd.less'
import 'imports/rxjs'
import {LocaleProvider} from 'antd'
import enUs from 'antd/es/locale-provider/en_US'

const App = <Provider customReducers={reducers} customEpics={epics} axiosInstance={axiosInstance} lang="en">
    <LocaleProvider locale={enUs}>
        <Layout/>
    </LocaleProvider>
</Provider>

render(App, document.getElementById('root'))
