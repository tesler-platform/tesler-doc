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
import {
    historyObj,
    Link,
    isViewNavigationGroup,
    isViewNavigationItem
} from '@tesler-ui/core'
import {ViewMetaResponse} from '@tesler-ui/core/interfaces/view'
import styles from './SecondLevelTabs.less'
import {
    ViewNavigationItem,
    MenuItem
} from '@tesler-ui/core/interfaces/navigation'
import {ViewNavigationGroup} from 'interfaces/navigation'

export interface ViewNavigationProps {
    views: ViewMetaResponse[],
    activeView: string,
    navigationMenu: MenuItem[],
    bcPath: string,
    navigationLevel?: number
}

interface ViewParseInfo {
    name: string,
    customTitle?: string,
    selected?: boolean,
}

export function SecondLevelTabs(props: ViewNavigationProps) {
    const handleChange = (key: string) => {
        historyObj.push(key)
    }

    let parsedViews: ViewParseInfo[] = []
    const activeView = props.views.find((v) => v.url === props.activeView)
    const activeViewName = activeView && activeView.name

    if (activeViewName) {
        let getTabsResult: any = null
        props.navigationMenu.some((navItem) => {
            return !!(getTabsResult = getTabsFromNavGroup(activeViewName, navItem, props.navigationLevel || 2, 1))
        })

        if (getTabsResult && 'tabList' in getTabsResult) {
            parsedViews = getTabsResult.tabList
        }
    }

    let selectedTabViewName = props.activeView
    const levelViews: Array<{name: string, url: string}> = []
    parsedViews.forEach((parsedView) => {
        const view = props.views.find((v) => v.name === parsedView.name)
        if (view) {
            levelViews.push({
                name: (parsedView.customTitle) ? parsedView.customTitle : view.title,
                url: view.url
            })

            if (parsedView.selected) {
                selectedTabViewName = view.url
            }
        }
    })

    return <nav className={styles.container}>
        <Tabs
            activeKey={`${selectedTabViewName}/${props.bcPath}`}
            tabBarGutter={24}
            size="large"
            onChange={handleChange}
        >
            {levelViews.map((item) =>
                <Tabs.TabPane
                    key={`${item.url}/${props.bcPath}`}
                    tab={<Link href={`${item.url}/${props.bcPath}`} className={styles.link}>
                        <span>{item.name}</span>
                    </Link>}
                />
            )}
        </Tabs>
    </nav>
}

const getItemInfo = (menuItem: ViewNavigationGroup | ViewNavigationItem): ViewParseInfo => {
    if (isViewNavigationGroup(menuItem)) {
        return {
            name: getItemInfo(menuItem.child[0] as ViewNavigationItem).name,
            customTitle: menuItem.title
        }
    } else if (isViewNavigationItem(menuItem)) {
        return {
            name: menuItem.viewName
        }
    }
}

const getCategoryTabs = (
    categoryData: ViewNavigationGroup,
    selectedItem: ViewNavigationItem
) => {
    const tabs: ViewParseInfo[] = []

    categoryData.child.forEach((child) => {
        const tab = getItemInfo(child)
        if (child === selectedItem) {
            tab.selected = true
        }
        tabs.push(tab)
    })

    return tabs
}

/**
 * TODO: JSDOC
 */
const getTabsFromNavGroup = (
    viewName: string, menuItem: MenuItem, targetLevel: number, currentLevel: number
): { tabList: ViewParseInfo[] } | MenuItem => {
    if (isViewNavigationGroup(menuItem) && currentLevel < 2) {
        let tabList: {tabList: ViewParseInfo[]}

        menuItem.child.find((child) => {
            const searchResult = getTabsFromNavGroup(viewName, child, targetLevel, currentLevel + 1)
            if (searchResult && 'tabList' in searchResult) {
                tabList = searchResult
                return true
            }
        })

        return tabList
    } else if (isViewNavigationGroup(menuItem) && currentLevel > 1) {
        let tabList: {tabList: ViewParseInfo[]}

        const selectedChild = menuItem.child.find((child) => {
            const searchResult = getTabsFromNavGroup(viewName, child, targetLevel, currentLevel + 1)

            if (searchResult && 'tabList' in searchResult) {
                tabList = searchResult
                return false
            }

            return !!searchResult
        })

        if (selectedChild && currentLevel === targetLevel) {
            return {tabList: getCategoryTabs(menuItem as ViewNavigationGroup, selectedChild as ViewNavigationItem)}
        }

        return tabList || selectedChild
    } else if (isViewNavigationItem(menuItem) && menuItem.viewName === viewName) {
        return menuItem
    }

    return null
}

export default React.memo(SecondLevelTabs)
