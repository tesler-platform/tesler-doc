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
import {connect} from '@tesler-ui/core'
import {SessionScreen} from '@tesler-ui/core/interfaces/session'
import ScreenNavigation from 'components/ui/ScreenNavigation/ScreenNavigation'
import * as styles from './AppSideMenu.less'
import {AppState} from 'interfaces/storeSlices'

export interface AppSideMenuProps {
    screenName: string,
    screenUrl: string,
    sessionScreens: SessionScreen[],
    menuVisible: boolean,
    mobileMenu: boolean
    onMenuVisible: (menuVisible: boolean) => void
}

export function AppSideMenu(props: AppSideMenuProps) {
    return (
        <React.Fragment>
            <div className={styles.logoContainer}
                onClick={() => {props.onMenuVisible(!props.menuVisible)}}>
                <div className={styles.textlogo}>
                    {!props.mobileMenu && props.menuVisible ? 'Tesler' : 'T'}
                </div>
            </div>
            <div className={styles.navigation}>
                <ScreenNavigation
                    items={props.sessionScreens}
                    selectedScreen={props.screenUrl}
                />
            </div>
        </React.Fragment>
    )
}

function mapStateToProps(store: AppState) {
    const selectedScreen = store.session.screens.find(item => item.name === store.router.screenName)
    const screenUrl = selectedScreen && selectedScreen.url
        || `/screen/${store.router.screenName}`
    return {
        screenName: store.router.screenName,
        screenUrl,
        sessionScreens: store.session.screens,
        menuVisible: store.screen.menuVisible,
        mobileMenu: store.screen.mobileMenu
    }
}

export default connect(mapStateToProps)(AppSideMenu)
