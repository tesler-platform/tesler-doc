# View

View is UI component that binds to URL and displayed single application page.

![image](https://user-images.githubusercontent.com/11893831/73737065-f1edb800-4752-11ea-9b93-3984c3e4a4b7.png)


# API

|  Property |  Description | Type  |
|:---|:---|:---|
| **name**  | Unique name of View, must match with file name (i.e. exampleView.view.json) | `string`  |
| **title**  | Name of view in navigation bar. Part of interface, that user can see. template - view template, which is defined on UI side. Responsible for the format, how widgets will be displayed. | `string`  |
| **url**  | path of view in URL in following format: /screen/screen_namge/view/view_name | `string`  |
| **widgets**  | array of widgets, that displayed on current view: <ul><li>*widgetName* - Unique name of Widget, must match with widget.json file name (i.e. example.widget.json)</li><li>*position* - position of current widget in view template</li><li>*gridWidth* - if template supports</li></ul>|  `WidgetMeta[]` |

### Example of view.json file

 
```json 
{
  name : "exampleView",
  title : "Example View Title",
  template : "DashboardView",
  url : "/screen/components/view/exampleView",
  widgets : [ {
    widgetName : "exampleWidget1",
    position : 0,
    gridWidth : 2
  }, {
    widgetName : "exampleWidget2",
    position : 1,
    gridWidth : 2,
  } ]
}
```