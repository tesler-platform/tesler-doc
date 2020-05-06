# Views

To be able to select a view your application layout should contain a dedicated view navigation component. The simpliest way is to build it  around `useViewTabs` hook which will give you an array of views and their corresponding urls:

```tsx
// ViewNavigation.tsx
import React from 'react'
import {useViewTabs} from '@tesler-ui/core'

export const ViewNavigation: React.FC = (props) => {
    // Get views of the active screen
    const tabs = useViewTabs(1) // 1 represents a top level views, i.e. the first level of navigation
    return <ul>
        { tabs.map(item => {
            <li key={item.url}>
                <a href={item.url}>
                    {item.title}
                </a>
            </li>
        })}
    </ul>
}
```

Then you can put this component in your application layout component:

```tsx
// AppLayout.tsx
import React from 'react'
import {View} from '@tesler-ui/core'

export const AppLayout: React.FC = (props) => {
    return <div>
        {/* Will render our view navigation menu */}
        <ViewNavigation />
        <hr />
        {/* Will render currently active view */}
        <View /> 
    </div>
}
```

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

## Additional customization

You can also build your view navigation component from scratch by connecting it directly to the views and corresponding meta data in the store, though this will require some insights on how Tesler API returns a meta data for screens.  
You can check the [Major concepts](#/screen/getting-started/view/screen/) section for meta data description and [useViewTabs](https://github.com/tesler-platform/tesler-ui/blob/master/src/hooks/useViewTabs.ts) [source code](https://github.com/tesler-platform/tesler-ui/blob/master/src/utils/viewTabs.ts) for a reference implementation.

```tsx
import React from 'react'
import {connect} from '@tesler-ui/core'
import {AppState} from 'reducers'
import {ViewMetaResponse} from '@tesler-ui/core/interfaces/view'
import {MenuItem} from '@tesler-ui/core/interfaces/navigation'

interface ViewNavigationProps {
    views: ViewMetaResponse[],
    navigation: MenuItem[]
}

export const ViewNavigation: React.FC<ViewNavigationProps> = (props) => {
    // implement your logic here
}

function mapStateToProps(store: AppState) {
    return {
        // an array of views of currently active screen
        views: store.screen.views,
        // a navigation meta data for currently active screen
        navigation: store.session.screens.find(screen => screen.name === store.screen.screenName)?.meta.navigation.menu
    }
}

export default connect(mapStateToProps)(ViewNavigation)
```