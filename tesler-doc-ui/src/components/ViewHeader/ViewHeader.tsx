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
import {connect, $do} from '@tesler-ui/core'
import {AppState} from 'reducers'
import * as styles from './ViewHeader.less'
import {SecondLevelTabs} from 'components/ui/SecondLevelTabs/SecondLevelTabs'
import {ViewMetaResponse} from '@tesler-ui/core/interfaces/view'
import {WidgetMeta, WidgetTypes} from '@tesler-ui/core/interfaces/widget'
import {MenuItem} from '@tesler-ui/core/interfaces/navigation'
import {TeslerScreenResponse} from 'interfaces/navigation'
import {Dispatch} from 'redux'
import {Route} from '@tesler-ui/core/interfaces/router'

interface ViewHeaderOwnProps {

}

interface ViewHeaderProps extends ViewHeaderOwnProps {
    title: string,
    views: ViewMetaResponse[],
    activeView: string,
    widgets: WidgetMeta[],
    navigationMenu: MenuItem[],
    route: Route,
    bcPath: string,
    headerWidth: any,
    onDrilldown: (url: string, route: Route) => void
}

export function ViewHeader(props: ViewHeaderProps) {
    const showSecondMenu = !!(props.widgets || []).find((v) => v.type === WidgetTypes.SecondLevelMenu)

    return <React.Fragment>
        <Row className={styles.headerContainer} type="flex" justify="center">
            <div className={styles.headerWrapper}>
                <div className={styles.tabs} style={props.headerWidth}>
                    {(showSecondMenu) &&
                        <SecondLevelTabs
                            activeView={props.activeView}
                            views={props.views}
                            navigationMenu={props.navigationMenu}
                            bcPath={props.bcPath}
                        />
                    }
                </div>
            </div>
        </Row>
    </React.Fragment>
}

function mapStateToProps(store: AppState, ownProps: ViewHeaderOwnProps) {
    const sessionScreen = store.session.screens.find(screen => screen.name === store.screen.screenName)
    const teslerScreenMeta = sessionScreen && sessionScreen.meta as TeslerScreenResponse
    return {
        title: store.view.title,
        bcPath: store.router.bcPath,
        views: store.screen.views,
        activeView: store.view.url,
        widgets: store.view.widgets,
        navigationMenu: teslerScreenMeta && teslerScreenMeta.navigation.menu,
        route: store.router
    }
}

function mapDispatchToProps(dispatch: Dispatch) {
    return {
        onDrilldown: (url: string, route: Route) => {
            dispatch($do.drillDown({ url, route }))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(ViewHeader)
