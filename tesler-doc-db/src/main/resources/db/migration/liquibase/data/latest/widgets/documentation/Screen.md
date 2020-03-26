# Screen

Screen is a navigation container that contains view elements.

- The screen represents a logical grouping of views that pertain to one business function.
- To simplify navigation, you can group views in a screen into categories.
- All the views in a screen usually reference a single business object.


The user can access a screen through a screen tab. These links to each screen are defined as part of the page tab object definition, which is a child of the screen. The screen defines the default view that Tesler displays if the user clicks a screen tab.

![image](https://user-images.githubusercontent.com/11893831/73737065-f1edb800-4752-11ea-9b93-3984c3e4a4b7.png)


Screen metadata stored on screen.json file.

# API

|  Property |  Description | Type  |
|:---|:---|:---|
| **name**  | unique name of Screen, must match with file name (i.e. example.screen.json)  | `string`  |
| **title**  | name of screen in navigation menu. Part of interface, that user can see. | `string` |
| **primaryViewName**  |   Name of view that opens when user click on screen in navigation bar. | `string` |
| **primaryViews**  | if user don't have permission to open view in primaryViewName field (for example because it does not specify in responsibilities), you can specify array of views in this field. The first view available to the user will be chosen. |  `array` |
| **navigation** | list of views and group of views with a tree structure. Determines how the user menu will look like | `object`  |

\* - required

# Navigation on Screen

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


# Rules:
- A group must have 2 or more children (both group and view)
- Header (menu) block is a group, but can have only 1 children

![0](https://user-images.githubusercontent.com/11893831/73404884-a32fc080-4303-11ea-90fa-d37dbf70d8c0.png)

The navigation structure shown above will be presented in a json file as follows:

```ts
{
  "_comment": "Other screen metadata goes above",
  navigation: {
    menu: [{
      viewName: "soloView"
    }, {
      title: "First Group",
      child: [{
        viewName: "showedView"
      }, {
        hidden: true,
        viewName: "hiddenView"
      }]
    }, {
      title: "3 Group",
      child: [{
        title: "Nested Group",
        child: [{
          viewName: "nestedGroupFirstView"
        }, {
          viewName: "nestedGroupSecondView"
        }]
      }, {
        viewName: "secondGroupView"
      }]
    }, {
      title: "4 Group",
      child: [{
        title: "1 Nested Group",
        child: [{
          viewName: "firstNestedGroupFirstView"
        }, {
          viewName: "firstNestedGroupSecondView"
        }]
      }, {
        title: "2 Nested Group",
        child: [{
          viewName: "secondNestedGroupFirstView"
        }, {
          viewName: "secondNestedGroupSecondView"
        }]
      }]
    }]
  }
```

# Bad Cases:
- A group without children
- A group with one children.

![1](https://user-images.githubusercontent.com/11893831/73404895-a7f47480-4303-11ea-9349-79823bac94ea.png)

If group does not specify defaultView field, clicking a group opens the first view, which is found using the breadth-first search algorithm. 

![2](https://user-images.githubusercontent.com/11893831/73404897-aa56ce80-4303-11ea-8e0e-0cf3929635d0.png)

The first level of navigation tab is presented in the application header.
In order to navigate the lower levels of navigation, it is possible to display special navigation tab widgets

![3](https://user-images.githubusercontent.com/11893831/73404899-ab87fb80-4303-11ea-9152-5bd23a5f5874.png)


### Example of screen.json file

```ts
{
    "name": "example",
    "title": "Example screen",
    "primaryViewName": "oneWidgetView",
    "primaryViews": [
        "oneWidgetView"
    ],
    "navigation": {
        "menu": [
            {
              "title": "Some group",
              "child": [
                  {"viewName": "firstView"},
                  {"viewName": "secondView"}
              ]
            },
            {
              "_comment": "other navigation elements here"
            }
        ]
    }
}
```

