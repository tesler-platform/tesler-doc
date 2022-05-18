# Widget

Widget is UI component that works with user information according to the widget type.

# API

## Form API

|  Property |  Description | Type  |
|:---|:---|:---|
| **type**  | type of widget: <ul><li>List</li><li>Form</li></ui>  | `string`  |
| **title**  | title of widget displayed on view  | `string`  |
| **bc**  |  key for the business component that will be used for data sampling | `string`  |
| **fields**  | an array containing objects that describe the widget fields  |  `object[]` |
| **position**  |  widget position on view | `number`  |
| **gridWidth**  |  widget width | `number`  |
| **options** |  optional parameter containing additional options |  `object` |
| **limit** |  Optional parameter containing limit number of records per page which could will be got by one request. Has higher priority then `limit` value from `BC` metadata. Available since `@tesler-ui/core: 1.14.0` |  `number` |

## Field API

The field api also changes depending on the widget type


### Custom operations

To use different operations, use the parameter `operations.actionGroups`. This parameter includes two arrays, `include` and `exclude`, which contain operation types.

### Full data example of Form widget for using

```json
{   
    title: "Example",
    bc: "exampleBC",
    type: "Form",
    position: 1,
    gridWidth: 24,
    fields: [
        {"label": "Name", "key": "name", "type": "input"},
        {"label": "Age", "key": "age", "type": "input"},
        {"label": "Country", "key": "country", "type": "input"}
    ],
    options: {
        "actionGroups": {
            "include": ["create", "cancel"],
            "exclude": []
        },
        layout: {
            rows: [
                {"cols": [{"fieldKey": "name", "span": 24, "type": "input"}]},
                {"cols": [{"fieldKey": "age", "span": 24, "type": "input"}]},
                {"cols": [{"fieldKey": "country", "span": 24, "type": "input"}]},
              ]
        }
    }
}
```
