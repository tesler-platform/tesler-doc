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

import React from 'react'
import {Row} from 'antd'
import {connect} from '@tesler-ui/core'
import {AppState} from 'interfaces/storeSlices'
import * as styles from './ViewHeader.less'
import {SecondLevelTabs} from 'components/ui/SecondLevelTabs/SecondLevelTabs'
import {WidgetMeta, WidgetTypes} from '@tesler-ui/core/interfaces/widget'
import {Route} from '@tesler-ui/core/interfaces/router'

interface ViewHeaderOwnProps {
    headerWidth: React.CSSProperties
}

interface ViewHeaderProps extends ViewHeaderOwnProps {
    widgets: WidgetMeta[],
    route: Route,
    bcPath: string
}

export function ViewHeader(props: ViewHeaderProps) {
    const showSecondMenu = props.widgets?.some((v) => v.type === WidgetTypes.SecondLevelMenu)
    // TODO: Combine wrapper divs
    return <Row className={styles.headerContainer} type="flex" justify="center">
        <div className={styles.headerWrapper}>
            <div className={styles.tabs} style={props.headerWidth}>
                { showSecondMenu && <SecondLevelTabs bcPath={props.bcPath} /> }
            </div>
        </div>
    </Row>
}

function mapStateToProps(store: AppState, ownProps: ViewHeaderOwnProps) {
    return {
        bcPath: store.router.bcPath,
        widgets: store.view.widgets,
        route: store.router
    }
}

export default connect(mapStateToProps)(ViewHeader)
