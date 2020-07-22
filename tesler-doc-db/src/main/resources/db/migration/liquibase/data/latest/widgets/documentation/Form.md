# Form

Form displays selected row from business component.

## When to use
- When you need to create a instance or collect information.
- When you need to validate fields in certain rules.

# API

## Form API

|  Property |  Description | Type  |
|:---|:---|:---|
| **type**  | type of widget 'Form'  | `string`  |
| **title**  | title of widget displayed on view  | `string`  |
| **bc**  |  key for the business component that will be used for data sampling | `string`  |
| **fields**  | an array containing objects that describe the fields that make up the form  |  `object[]` |
| **position**  |  widget position on view | `number`  |
| **gridWidth**  |  widget width | `number`  |
| **options** |  optional parameter containing additional options |  `object` |

## Field API

| Property | Description  | Type |
|:---|:---|:---|
| **label** | field name  | `string`  |
| **key** | the key of the record field that the form field corresponds to  | `string`  |
| **type** | field type that defines how data is displayed in the form field  | <ul><li>`Input`</li><li>`Picklist`</li><li>`LOV`</li><li>`Number`</li><li>`Checkbox`</li><li>`Date`</li></ul> |

## Layout settings

The `options.layout` property is used to configure the location of fields inside the form.This property has the following type:

```json
{
    "layout"?: {
       "rows": Array<{
           "cols": Array<{
               "fieldKey": string, "span": number
           }>
        }>
    }
}
```

| Property | Description  | Type |
|:---|:---|:---|
| **rows** | array of objects with parameters for each form line |  `object[]`  |
| **cols** | an array of objects containing column parameters for form  | `object[]`  |
| **fieldKey** | a field key that specifies which field to insert in this grid cell  | `string` |
| **span** | width of cell (max 24) | `number`  |


### Example of forming a layout

```json
{
    "rows": [
        {"cols": [
            {"fieldKey": "name", "span": 16, "type": "input"},
            {"fieldKey": "active-projects-amount", "span": 8, "type": "input"}
        ]},
        {"cols": [
            {"fieldKey": "country", "span": 8, "type": "input"},
            {"fieldKey": "national", "span": 8, "type": "input"},
            {"fieldKey": "size", "span": 8, "type": "input"}
        ]},
        {"cols": [
            {"fieldKey": "notes", "span": 24, "type": "input"}
        ]}
      ]
}
```

With these settings, the layout of the widget will look like this:

<table>
    <tr>
        <td colspan="2">Name</td>
        <td>active-projects-amount</td>
    </tr>
    <tr>
        <td>country</td>
        <td>national</td>
        <td>size</td>
    </tr>
    <tr>
        <td colspan="3">notes</td>
    </tr>
  </table>


### Custom operations

To use different operations, use the parameter `operations.actionGroups`. This parameter includes two arrays, `include` and `exclude`, which contain operation types.

### Full data example for using

```json
{   
    "title": "Example",
    "bc": "exampleBC",
    "type": "Form",
    "position": 1,
    "gridWidth": 24,
    "fields": [
        {"label": "Name", "key": "name", "type": "input"},
        {"label": "Age", "key": "age", "type": "input"},
        {"label": "Country", "key": "country", "type": "input"}
    ],
    "options": {
        "actionGroups": {
            "include": ["create", "cancel"],
            "exclude": []
        },
        "layout": {
            "rows": [
                {"cols": [{"fieldKey": "name", "span": 24, "type": "input"}]},
                {"cols": [{"fieldKey": "age", "span": 24, "type": "input"}]},
                {"cols": [{"fieldKey": "country", "span": 24, "type": "input"}]},
              ]
        }
    }
}
```
