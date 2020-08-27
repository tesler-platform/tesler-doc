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
import {connect} from 'react-redux'
import {Avatar, Button, Popover, Row, Icon} from 'antd'
import ViewNavigation from 'components/ui/ViewNavigation/ViewNavigation'
import * as styles from './AppBar.less'
import {$do} from '@tesler-ui/core'
import {Dispatch} from 'redux'
import {AppState} from 'interfaces/storeSlices'
import cn from 'classnames'

interface AppBarOwnProps {
    headerWidth: React.CSSProperties,
    onMenuVisible: (menuVisible: boolean) => void
}

interface AppBarProps extends AppBarOwnProps {
    fullName: string,
    login: string,
    mobileMenu: boolean,
    menuVisible: boolean,
    onLogout: () => void
}

export function AppBar(props: AppBarProps) {
    return <Row className={styles.headerContainer} type="flex" justify="center">
        <div
            className={
                cn(
                    styles.container,
                    { [styles.mobileContainer]: props.mobileMenu },
                    { [styles.menuVisible]: props.menuVisible }
                )
            }
            style={props.headerWidth}
        >
        {props.mobileMenu
            && <Icon
                onClick={() => {props.onMenuVisible(!props.menuVisible)}}
                type={cn({
                    'menu-unfold': !props.menuVisible,
                    'menu-fold': props.menuVisible,
                })}
            />
        }
            <ViewNavigation />
        </div>
        <div className={styles.controls}>
            <Popover
                overlayClassName={styles.userInfoPopover}
                trigger="click"
                placement="bottomRight"
                content={userMenu(props)}
            >
                <Avatar size={24} icon="user" className={styles.userIcon}/>
            </Popover>
        </div>
    </Row>
}

function userMenu(props: AppBarProps) {
    const onLogout = props.onLogout
    return <div className={styles.settings}>
        <div className={styles.fullName}>{props.fullName}</div>
        <div className={styles.login}>{props.login}</div>
        <Button onClick={onLogout} icon="logout">
            Logout
        </Button>
    </div>
}

function mapStateToProps(state: AppState) {
    return {
        fullName: state.session.fullName,
        login: state.session.login,
        menuVisible: state.screen.menuVisible,
        mobileMenu: state.screen.mobileMenu
    }
}

function mapDispatchToProps(dispatch: Dispatch) {
    return {
        onLogout: () => {
            dispatch($do.logout(null))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(AppBar)
