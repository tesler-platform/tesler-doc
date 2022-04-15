# List

## Back-end implementation (meta data)

List/Table displays rows of data.



### When to use

- To display a collection of structured data.
- To sort, search, paginate, filter data.



### How to use

Specify *business components* of Table as an array of data.



## API

### List 

|  Property |  Description | Type  |
|:---|:---|:---|
| **type***  | type of widget `List`  | `string`  |
| **title***  | title of widget displayed on view  | `string`  |
| **bc***  |  key for the business component that will be used for data sampling | `string`  |
| **fields***  | an array containing objects that describe the table columns and their corresponding fields on the selected records  |  `WidgetListField[]` |
| **position*** | the order in which widgets will be positioned relative to each other: widgets with lesser position will be displayed first, and with larger will be displayed last | `number`  |
| **gridWidth***  | the width of widget in grid columns ([see example](https://ant.design/components/grid/)). Currently unused in core layouts, could be used for custom layouts with other grid systems. | `number`  |
| **options**  |  optional parameter containing additional options |  `object[]` |



### WidgetListField

The `fields` array is used to determine which columns will appear on the list widget and how they will display the data in their cells.

| Property | Description  | Type |
|:---|:---|:---|
| **title*** | displayed title for the column  | `string`  |
| **key*** | record field that the column field corresponds to  | `string`  |
| **type*** | control which will be used to display the data in a cell  | <ul><li>`Input`</li><li>`Picklist`</li><li>`LOV`</li><li>`Number`</li><li>`Checkbox`</li><li>`Date`</li></ul> |
| **hintKey** | Key of the BC field containing the transcript or explanation for the abbreviated value  | `string`  |
| **width** | default width of the column  | `number`  |
| **drillDown** | enables the possibility of a drildown in this column | `boolean`  |
| **bgColor** | color (#FFFFFF) that will be used as a cell background | `string`  |
| **bgColorKey** | record field that will be used as a source for cell background color | `string`  |

### Data fetching

The `bc` property of the `List` widget is used to fetch the data entities of that specific business component.
Generally, the Tesler API will respond to the corresponding data request with an array of `DataItem` objects, which will be displayed as table rows.

`DataItem` objects have an `id` property which is interpreted as a unique key of this particular record, and all other non-reserved properties represent a key/value pair, where key should match a `key` property of the `WidgetListField` description, and value will be used as a cell value for this columns: 

```ts
// Tesler API response
{
    "success": true,
    "data": [
        {
            // used as unique record key
            "id": "1",
            // Other fields could be referenced by they `key` 
            "name": "Mike"
            "country": "Canada"
            "age": 20
        },
        {
            "id": "2",
            "name": "Kate"
            "country": "France"
            "age": 25
        },
        {
            "id": "3",
            "name": "Christian"
            "country": "Germany"
            "age": 30
        }
    ],
    "hasNext": false
}
```


### Custom operations

To use different operations, use the parameter `operations.actionGroups*`. This parameter includes two arrays, `include` and `exclude`, which contain operation types.



#### Full data example for using

 
```ts 
{
    title: "Example",
    bc: "exampleBC",
    type: "List",
    position: 1,
    gridWidth: 24,
    fields: [
        {title: "Name", key: "name", drillDown: "true", type: "input"}
        {title: "Country", key: "country", type: "input"}
        {title: "Age", key: "age", type: "input"}
    ],
    options: {
        actionGroups: {
            include: ["create", "cancel"],
            exclude: [],
        }
    }
}
```


The table will look like this

|  Name |  Country | Age  |
|:---:|:---:|:---:|
| Mike  | Canada  | 20  |
| Kate  | France  | 25  |
| Christian  | Germany  | 30  |
