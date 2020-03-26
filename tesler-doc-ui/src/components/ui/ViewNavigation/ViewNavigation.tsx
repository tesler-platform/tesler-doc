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
import {Tabs} from 'antd'
import {historyObj, isViewNavigationGroup} from '@tesler-ui/core'
import {ViewMetaResponse} from '@tesler-ui/core/interfaces/view'
import {ViewNavigationItem as CoreViewNavigationItem} from '@tesler-ui/core/interfaces/navigation'
import {ViewNavigationGroup} from '@tesler-ui/core/interfaces/navigation'
import styles from './ViewNavigation.less'

export interface ViewNavigationProps {
    views: ViewNavigationItem[],
    activeView: string
}

export interface ViewNavigationItem {
    id: number,
    title: string,
    url: string
}

export function ViewNavigation(props: ViewNavigationProps) {

    const handleChange = (key: string) => {
        historyObj.push(key)
    }

    return <nav className={styles.container}>
        <Tabs
            activeKey={props.activeView}
            tabBarGutter={24}
            size="large"
            onChange={handleChange}
        >
            {props.views.map((item) =>
                <Tabs.TabPane
                    key={item.url}
                    tab={<span className={styles.item}>
                            {item.title}
                        </span>
                    }
                />
            )}
        </Tabs>
    </nav>
}

/**
 * TODO: JSDOC
 */
export function useViewNavigation(
    menu: ViewNavigationGroup[],
    views: ViewMetaResponse[]
): ViewNavigationItem[] {
    return React.useMemo(() => {
        if (!menu || !views) {
            return []
        }

        return menu[0].child.map(item => {
            let title = ''
            let viewName = ''
            if (isViewNavigationGroup(item)) {
                title = item.title
                viewName = item.child && item.child[0] && (item.child[0] as CoreViewNavigationItem).viewName
            } else {
                viewName = (item as CoreViewNavigationItem).viewName
            }
            const matchingView = views.find(view => view.name === viewName)
            return matchingView && {
                id: matchingView.id,
                title: title || matchingView.title,
                url: matchingView.url
            }
        }).filter(item => !!item)
    }, [menu, views])
}

export default React.memo(ViewNavigation)
