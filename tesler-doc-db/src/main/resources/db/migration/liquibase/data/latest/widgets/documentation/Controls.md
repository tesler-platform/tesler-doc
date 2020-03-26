# Controls

List/Form fields display special data type.



## When to use

- When a numeric value needs to be provided.
- Used if you need checkbox.
- By clicking the input box, you can select a date from a popup calendar.



## How to use

Set the required type in fields meta and use the special application components.


# API


## Number


To display numbers, fields of the types: `number`, `percent`, `money` are used.
Properties for `NumberInput` component.

| Property | Description  | Type | Default
|:---|:---|:---|:---|
| **value*** | the input content value  | `number`  | - |
| **type*** | the type of input | `number` \| `percent` \| `money`  | - |
| **readOnly** | whether the input is read only  | `boolean`  | undefined |
| **disabled** | whether the input is disabled | `boolean`  | undefined |
| **backgroundColor** | color of background  | `string`  | undefined |
| **onChange** | callback when user input | `function(value: number)`  | - |
| **nullable** | allows to set the value to a special value "NULL" | `boolean`  | undefined |
| **digits** | number of characters after point  | `number`  | - |
| **maxInput** | maximum number of characters  | `number`  | undefined |
| **className** | className of this input | `string`  | '' |
| **onDrillDown** | callback when user click link of input  | `function()`  | - |
| **forceFocus** | get focus when component mounted | `boolean`  | undefined |


## Checkbox


To display checkboxes, fields of the type `checkbox` are used.
Properties for `CheckboxPicker` component.

| Property | Description  | Type | Default
|:---|:---|:---|:---|
| **fieldName*** | the key name of the checkbox  | `string`  | - |
| **fieldLabel*** | the name of the checkbox | `string`  | - |
| **bcName*** | the name of the current business component  | `string`  | - |
| **cursor*** | currently active record | `string`  | - |
| **value*** | the checkbox content value | `string` \| `number` \| `boolean` \| `null` \| `[id: string, value: string]` \| `undefined`  | - |
| **readOnly** | whether the checkbox is read only  | `boolean`  | undefined |


## Date


To display dates, fields of the types: `date`, `dateTime`, `dateTimeWithSeconds` are used.
Properties for `DatePickerField` component.

| Property | Description  | Type | Default
|:---|:---|:---|:---|
| **readOnly** | whether the DatePicker is read only  | `boolean`  | undefined |
| **disabled** | determine whether the DatePicker is disabled | `boolean`  | undefined |
| **value** | to set date  | `string` \| `null`  | - |
| **onChange** | a callback function, can be executed when the selected time is changing | `function(date: string | null)`  | - |
| **showToday** | whether to show "Today" button  | `boolean`  | - |
| **allowClear** | whether to show clear button | `boolean`  | - |
| **onOpenChange** | a callback function, can be executed whether the popup calendar is popped up or closed | `function(status: boolean)`  | - |
| **disabledDate** | specify the date that cannot be selected | `(currentDate: moment) => boolean`  | - |
| **showTime** | to provide an additional time selection | `boolean`  | - |
| **monthYear** | whether to show only month and year | `boolean`  | undefined |
| **showSeconds** | whether to show seconds | `boolean`  | - |
| **backgroundColor** | color of background  | `string` \| `null`  | - |
| **className** | picker className | `string`  | '' |
| **resetForceFocus** | reset focus of the component | `function()`  | - |
| **dateFormatter** | format of the date | `(date: moment.Moment) => string`  | - |
| **calendarContainer** | container of DataPicker | `HTMLElement`  | - |
| **onDrillDown** | callback when user click link of DatePicker | `function()`  | - |
---

\* - required


## Example for using

```json
{   
    "title": "Example",
    "bc": "exampleBC",
    "type": "List",
    "fields": [
        {"title": "money", "key": "testMoney", "type": "money"},
        {"title": "checkbox", "key": "testCheckbox", "type": "checkbox"},
        {"title": "date and time", "key": "testDate", "type": "dateTime"}
    ]
}
```
