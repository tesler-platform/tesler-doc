# PickListPopup

## Back-end implementation (meta data)
 `PickListPopup` widget displays modal window for selection single record for filling `pickList` field. 

### When to use

- When you need to fill `pickList` field.

## API

|  Property |  Description | Type  |
|:---|:---|:---|
| **type**  | type of widget 'PickListPopup'  | `string`  |
| **title**  | title of widget displayed on view  | `string`  |
| **bc**  |  key for the business component that will be used for data sampling | `string`  |
| **fields**  | an array containing objects that describe the fields that make up the form  |  `WidgetListField[]` |
| **position**  |  widget position on view | `number`  |
| **gridWidth**  |  widget width | `number`  |
| **options** |  optional parameter containing additional options |  `object` |

<br>

### Live example

You can check out general example below.  
//TODO fix example (BC of popup must has parent BC)
You can check out general example below.
<details>
<summary>How to interact with example?</summary>
<br>
Click on 'Create', then click on 'paperclip' icon.
</details>
