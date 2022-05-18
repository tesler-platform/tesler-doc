## Additional customization

You can also build your view navigation component from scratch by connecting it directly to the views and corresponding meta data in the store, though this will require some insights on how Tesler API returns a meta data for screens.  
You can check the [Major concepts](#/screen/getting-started/view/screen/) section for meta data description and [useViewTabs](https://github.com/tesler-platform/tesler-ui/blob/master/src/hooks/useViewTabs.ts) [source code](https://github.com/tesler-platform/tesler-ui/blob/master/src/utils/viewTabs.ts) for a reference implementation.

```tsx
import React from 'react'
import {connect} from '@tesler-ui/core'
import {AppState} from 'interfaces/storeSlices'
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
