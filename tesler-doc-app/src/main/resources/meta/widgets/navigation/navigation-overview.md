# Overview

Navigation is build around two major concepts: *views* and *screens* which form an url endpoints of the application.

[**View**](#/screen/getting-started/view/view/) is a basic unit of navigation representing a dashboard with [*widgets*](#/screen/getting-started/view/view/), so when the user initiates a navigation action he will always end up on some view or another. This page currently displays a view named [Navigation Overview](#/screen/components/view/navigation-overview/).

[**Screen**](#/screen/getting-started/view/screen/) is a group of views that have some logic in common and share their business components. For example, this page currently displays a view from the screen named [Components Overview](#/screen/components), but aside of navigation this screen contains a lot of other views describing different components.  

You may notice that the url does not always have a specified view name. Every screen has an option of specified the default view that will be opened when view name omitted from the url, and if no default view was provided than first available will be opened.

## User interactions for navigation

There are several ways how the user can navigate between the views in Tesler UI application:

1. By [navigating the screens](#/screen/components/view/navigation-screens/) via some `<ScreenNavigation />` component that will map the screens array from the store to their corresponding urls.
2. By [navigating the views](#/screen/components/view/navigation-views/) of the currently active screen through some `<ViewNavigation />` component that will map the views array to their corresponding urls.
3. By [navigating the tabs](#/screen/components/view/navigation-tabs/) on the currently active view through a special widget that supports multiple levels of nesting
4. By a *drilldown link* or correspondig *postInvoke action*
5. Directly through a browser address bar

## Menu layout

There are no requirements on where in the page layout these navigation components should be placed.  
This demo applications assumes that *screens* are selected from the side menu and *views* are selected in top application bar. Your application can switch them or use any other design approach.  
The layout specifics of each navigation component are explained in their corresponding sections.

## Navigation triggers

<!-- TODO: A lot of clarification is needed for each trigger -->
* `<a />` html tags
* [<Link /\> component](https://github.com/tesler-platform/tesler-ui/blob/master/src/components/ui/Link/Link.tsx)
* [<ActionLink /\> component](https://github.com/tesler-platform/tesler-ui/blob/master/src/components/ui/ActionLink/ActionLink.tsx)
* [changeLocation helper](https://github.com/tesler-platform/tesler-ui/blob/ed5e3e6851955efb331a05e3ce65590df0c10861/src/reducers/router.ts#L15)
* [Drilldowns](https://github.com/tesler-platform/tesler-ui/blob/ed5e3e6851955efb331a05e3ce65590df0c10861/src/epics/router.ts#L153)
* [History object instance](https://github.com/tesler-platform/tesler-ui/blob/ed5e3e6851955efb331a05e3ce65590df0c10861/src/reducers/router.ts#L8)

## Urls

Default format for urls is `${applicationRoot}/#/screen/${screenName}/view/${viewName}`.

<!-- TODO: Should reference a tutorial page -->
The exact format can be customized through `parseLocation` property of Tesler UI `<Provider />`.
