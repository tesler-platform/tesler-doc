## Predefined filters

To use predefined filters use parameter `filterGroups` in BcMeta.
This parameter should consists of array, each value of which contains 2 parameters: `name` and `filters`.

#### BcMeta

| Property | Description  | Type | Default
|:---|:---|:---|:---|
| **name*** | name of Business Component  | `string`  | - |
| **parentName*** | name of parent Business Component  | `string`  | null | 
| **url*** | address to the current Business Component  | `string` | - | 
| **cursor*** | currently active record  | `string`  | null |
| **defaultSort** | string representation of default bc sorters  | `string`  | null |
| **filterGroups** | predefined filters  | `object[]`  | null |


### FilterGroup

One of the BcMeta `filterGroups` prop for describing predefined filters.

| Property | Description  | Type | Default
|:---|:---|:---|:---|
| **name*** | title of predefined filters group  | `string`  | - |
| **filters*** | string representation of filters (example: 'someField1.contains=321&someField2.equalsOneOf=\["Confirmed", "Canceled"]')  | `string`  | - |

\* - required
