import React from 'react'
import {Tabs} from 'antd'
import {historyObj, useViewTabs} from '@tesler-ui/core'
import ExamplePreview from 'components/ui/ExamplePreview/ExamplePreview'

export const ViewNavigation: React.FC = (props) => {

    const tabs = useViewTabs(1)

    const handleChange = (key: string) => {
        historyObj.push(key)
    }
    const activeKey = tabs?.find(item => item.selected)?.url

    const content = <Tabs
        activeKey={activeKey}
        tabBarGutter={24}
        size="large"
        onChange={handleChange}
    >
        {tabs.map(item =>
            <Tabs.TabPane
                key={item.url}
                tab={<span>{item.title}</span>}
            />
        )}
    </Tabs>
    return <ExamplePreview>
        {content}
    </ExamplePreview>
}

ViewNavigation.displayName = 'NavEx2'

export default ViewNavigation
