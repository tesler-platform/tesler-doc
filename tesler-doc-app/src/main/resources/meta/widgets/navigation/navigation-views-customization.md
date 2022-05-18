## Customization

You can apply additional logic and styles around this hook:

```tsx
import React from 'react'
import {Tabs} from 'antd'
import {historyObj, useViewTabs} from '@tesler-ui/core'

export function ViewNavigation() {

    const tabs = useViewTabs(1)

    const handleChange = (key: string) => {
        historyObj.push(key)
    }

    return <Tabs
        activeKey={tabs?.find(item => item.selected)?.url}
        tabBarGutter={24}
        size="large"
        onChange={handleChange}
    >
        {tabs.map(item =>
            <Tabs.TabPane
                key={item.url}
                tab={<span className={styles.item}>
                        {item.title}
                    </span>
                }
            />
        )}
    </Tabs>
}
```

You can read more about `useViewTabs` hook in [Tabs section](#/screen/components/view/navigation-tabs/) as top level of view navigation is a specific case of a more general view navigation scenarion.
