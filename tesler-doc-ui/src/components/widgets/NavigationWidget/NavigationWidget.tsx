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

import React, {FunctionComponent} from 'react'
import {connect} from '@tesler-ui/core'
import {AppState} from 'interfaces/storeSlices'
import {SecondLevelTabs} from 'components/ui/SecondLevelTabs/SecondLevelTabs'
import {WidgetMeta} from '@tesler-ui/core/interfaces/widget'

export interface NavigationWidgetOwnProps {
    meta: WidgetMeta
}

export interface NavigationWidgetProps extends NavigationWidgetOwnProps {
    bcPath: string
}

export const NavigationWidget: FunctionComponent<NavigationWidgetProps> = (props) => {
    return <SecondLevelTabs bcPath={props.bcPath} />
}

function mapStateToProps(store: AppState) {
    return {
        bcPath: store.router.bcPath
    }
}

export default connect(mapStateToProps)(NavigationWidget)
