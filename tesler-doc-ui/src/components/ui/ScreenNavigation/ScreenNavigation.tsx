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
import {Menu, Icon} from 'antd'
import {SelectParam} from 'antd/es/menu'
import {$do, changeLocation, connect} from '@tesler-ui/core'
import {SessionScreen} from '@tesler-ui/core/interfaces/session'
import * as styles from './ScreenNavigation.less'
import {AppState} from 'interfaces/storeSlices'
import {DrillDownType} from '@tesler-ui/core/interfaces/router'
import {useDispatch} from 'react-redux'

export interface ScreenNavigationProps {
    items: SessionScreen[],
    selectedScreen: string,
    menuVisible: boolean,
    mobileMenu: boolean,
}

export function ScreenNavigation(props: ScreenNavigationProps) {
    const screens: SessionScreen[] = props.items || []

    const dispatch = useDispatch()

    const handleScreen = (e: SelectParam) => {
        if (e.key === '/screen/api-reference') {
            dispatch($do.drillDown({
                drillDownType: DrillDownType.external,
                url: 'https://tesler-platform.github.io/tesler-ui',
                route: null
            }))
        } else {
            changeLocation(e.key)
        }
    }

    return <Menu
        className={styles.Container}
        selectedKeys={[props.selectedScreen]}
        onClick={handleScreen}
        theme="dark"
    >
    <Menu.Divider className={props.menuVisible ? styles.MenuDivider : styles.MenuDividerCollapsed}/>
        {screens.map((item) => {
            return (
                <Menu.Item
                    key={item.url}
                    className={!props.mobileMenu && props.menuVisible ? styles.Item : styles.ItemCollapsed}
                >
                    <span className={styles.MenuItemLink}>
                        <Icon className={styles.icon} type={item.icon ? item.icon : 'coffee'} />
                        <span>{!props.mobileMenu && props.menuVisible && item.text}</span>
                        {props.menuVisible && item.notification &&
                            <div className={styles.Notification}>{item.notification}</div>
                        }
                    </span>
                </Menu.Item>
            )
        })}
    </Menu>
}

function mapStateToProps(store: AppState) {
    return {
        menuVisible: store.screen.menuVisible,
        mobileMenu: store.screen.mobileMenu
    }
}

export default connect(mapStateToProps)(ScreenNavigation)
