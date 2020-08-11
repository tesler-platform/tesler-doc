### `<MultipleSelect/>` component API
| Property | Description  | Type |
|:---|:---|:---|
| **meta*** | Component's meta data | `MultipleSelectFieldMeta` |
| **className** | component class name | `string` |
| **disabled** | whether the field is disabled | `boolean` |
| **onChange** | called when value of field is change | `function(value: MultivalueSingleValue[])` |
| **readOnly** | whether the field is read only | `boolean` |
| **placeholder** | placeholder text | `string` |
| **value** | field value | `MultivalueSingleValue[]` |
| **values** | list of dropdown values to select from | `Array<{ value: string }>` |
Also you can pass a rest properties of antd's `SelectProps`.
<br/>

#### MultivalueSingleValue
| Property | Description  | Type |
|:---|:---|:---|
| **id*** | Item ID | `string` |
| **value*** | Item value | `string` |
---
\* - required

Also, you can check out another example of usage: [Multiple Select with Linked Values](#/screen/features/view/multipleselectlinkedvalues)
