## Properties for custom fields

This properties are passed as `props` to your custom field component:

|  Property |  Description | Type  |
|:---|:---|:---|
| **value**  | Data item value  | `DataValue`  |
| **onChange**  | The callback triggered when the value is changed.  | `(value: any) => void`  |
| **onBlur**  | The callback triggered when the field lost focus.  | `() => void`  |
| **widgetMeta**  | Widget description for the field | `WidgetField` |
| **widgetName**  | Name of the widget | `string` |
| **className**  | CSS class name | `string` |
| **disabled**  | Field is disabled | `boolean` |
| **placeholder**  | Text to show when no `value` was provied | `string` |
| **readOnly**  | Field is not available for edit | `boolean` |
| **metaError**  | Error message for this field | `string` |
| **onDrillDown**  | The callback triggered when field is set for drilldown and link is clicked | `() => void` |