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
        { tabs.map(item =>
            <li key={item.url}>
                <a href={item.url}>
                    {item.title}
                </a>
            </li>
        )}
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
        <ViewNavigation />
        <hr />
        <View /> 
    </div>
}
```
