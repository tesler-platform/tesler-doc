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
    useViewTabs
} from '@tesler-ui/core'
import styles from './SecondLevelTabs.less'
import {NavigationLevel} from '@tesler-ui/core/interfaces/navigation'

export interface ViewNavigationProps {
    bcPath: string,
    navigationLevel?: NavigationLevel
}

export function SecondLevelTabs(props: ViewNavigationProps) {
    const handleChange = (key: string) => {
        historyObj.push(key)
    }

    const tabs = useViewTabs(props.navigationLevel || 2)
    const activeTab = tabs?.find(item => item.selected)

    return <nav className={styles.container}>
        <Tabs
            activeKey={`${activeTab?.url}/${props.bcPath}`}
            tabBarGutter={24}
            size="large"
            onChange={handleChange}
        >
            {tabs.map(item =>
                <Tabs.TabPane
                    key={`${item.url}/${props.bcPath}`}
                    tab={<Link href={`${item.url}/${props.bcPath}`} className={styles.link}>
                        <span>{item.title}</span>
                    </Link>}
                />
            )}
        </Tabs>
    </nav>
}

export default React.memo(SecondLevelTabs)
