# Tabs
Tabs are needed to navigate at screen level between different views. They have their own hierarchy: first-level menu tabs are seen always on the current screen, second-level tabs are seen when you choose a tab from first-level menu, etc.

For example, on the current page you can see first-level menu (*"Navigation"*, *"Widgets"*, *"Fields"*) and current second-level menu (*"Overview"*, *"Screen"*, ...). If you choose another first-level tab, you will see another set of second-level tabs. 

More info about level-menu hierarchy you can find at *[Major Concepts](#/screen/getting-started/view/screen/)*

## How to create?
Tabs are set in **screen.json** files. First-level tabs are in **menu** block. It can have only a view or some child-views. For example:
```ts
{
  "name": "example",
  "title": "Example Title",
  "primaryViewName": "no-child-view", 
  //Primary view is a view set by default

  "navigation": {
    "menu": [
      {
        "viewName": "no-child-view"
      },
      {
        "title": "View with children",
        "child": [
        //Here come second-level tabs
            { "viewName": "first-child" },
            { "viewName": "second-child" }
            //children can also have their own children, making third-level menu and so on
        ]
      }
    ]
  }
}
```
To provide second-level menu, a view must have **SecondLevelMenu.widget.json**:
```ts
{
  "name" : "SecondLevelMenu",
  "title" : "Navigation",
  "type" : "SecondLevelMenu",
  "fields" : [ ]
}
```
**first-child.view.json**:
```ts
{
  "name": "first-child",
  "title": "First Child Title",
  "template": "DashboardView",
  "url": "/screen/example/view/first",
  "widgets": [
    {
      "widgetName": "SecondLevelMenu"
    },
    {
      "widgetName": "firstChildWidgetName",
      "position": 1
    }
  ]
}
```