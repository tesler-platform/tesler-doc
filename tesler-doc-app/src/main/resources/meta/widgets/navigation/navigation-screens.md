# Screens
## API

|  Property |  Description | Type  |
|:---|:---|:---|
| **name**  | unique name of Screen, must match with file name (i.e. example.screen.json)  | `string`  |
| **title**  | name of screen in navigation menu. Part of interface, that user can see. | `string` |
| **primaryViewName**  |   Name of view that opens when user click on screen in navigation bar. | `string` |
| **primaryViews**  | if user don't have permission to open view in primaryViewName field (for example because it does not specify in responsibilities), you can specify array of views in this field. The first view available to the user will be chosen. |  `array` |
| **navigation** | list of views and group of views with a tree structure. Determines how the user menu will look like | `object`  |

\* - required

## Navigation on Screen

The navigation metadata life cycle is as follows:

- Developers create metadata for navigation in screen.json files, in the navigation block.
- Next, using Liquibase migration, the data falls into 2 tables - SCREEN_VIEW_GROUP (with type NAVIGATION) and SCREEN_VIEW_GROUP_DATA
- After which, when client calling login API, all screen navigation is requested from the tables and transmitted to the client as tree

Navigation metadata consists of two objects - View and Group

View Json object consists of the following fields:

| Name  |JSON  | Required  | Default value | Description |
|:---|:---|:---|:---|:---|
| viewName | String | yes  |   | Unique name of view refers to the name of view.json file. Title of view in navigation tabs specified in “title” field in view.json file.|
| hidden | boolean  | no  | false | Indicates that the view is not showed in navigation tabs. Hidden views accessible via drilldown |


Group Json object consists of the following fields:


| Name  |JSON  | Required  | Default value | Description |
|:---|:---|:---|:---|:---|
| title | String | yes  |   | Title of category in navigation tabs|
| child | Array of Objects (Group or View)  | yes  |  | Array of navigation elements specified below group(View or inner Group) |
| hidden | boolean | no  | false  | Indicates that the group not showed in navigation tabs.|
| defaultView | String  | no  | false | Should be name of view, which is located below group (in child element or lower). If specified, click on group in navigation tab redirects on view with following name. If not specified, click on group in navigation tab redirects on first view, which is found using the breadth-first search algorithm |
