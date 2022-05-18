# Fields

List/Form fields display special data type.

## When to use

- When a one line text needs to be provided.
- When a numeric value needs to be provided.
- When a value needs to be selected from a predefined list of values.
- Used if you need checkbox.
- Used if you need radio buttons.
- By clicking the input box, you can select a date from a popup calendar.
- By clicking the input button, you can select a value from a popup list.

## How to use

Set the required type in fields meta and use the special application components.

## Example for using

```json
{
  "name": "bankListControls",
  "title": "On table",
  "type": "List",
  "bc": "bankDoc",
  "showCondition": [],
  "fields": [
    {
      "title": "Name",
      "key": "name",
      "type": "input",
      "drillDown": "true"
    },
    {
      "title": "Number",
      "key": "activeProjectsAmount",
      "type": "number"
    },
    {
      "title": "Percent",
      "key": "testPercent",
      "type": "percent"
    },
    {
      "title": "Money",
      "key": "testMoney",
      "type": "money"
    },
    {
      "title": "Checkbox",
      "key": "isNational",
      "type": "checkbox"
    },
    {
      "title": "Date",
      "key": "testDate",
      "type": "date"
    },
    {
      "title": "Date and Time",
      "key": "testDate",
      "type": "dateTime"
    },
    {
      "title": "Date and Time (with seconds)",
      "key": "testDate",
      "type": "dateTimeWithSeconds"
    },
    {
      "title": "Test Input",
      "key": "testInput",
      "type": "input"
    },
    {
      "title": "Pick List",
      "key": "testPickList",
      "type": "pickList",
      "popupBcName": "bankDocPicklist",
      "pickMap": {
        "testPickList": "name",
        "testInput": "activeProjectsAmount"
      }
    },
    {
      "title" : "Dictionary",
      "key" : "testDictionary",
      "type" : "dictionary"
    },
    {
      "title" : "Radio",
      "key" : "testRadio",
      "type" : "radio"
    }
  ],
  "axisFields": [],
  "chart": [],
  "options": {}
}
```
