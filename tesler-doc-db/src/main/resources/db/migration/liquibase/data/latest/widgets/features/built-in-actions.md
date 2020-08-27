# Built-in Actions

Basic actions include creating, updating, and deleting records

## When to use
- When you need to create new entry
- When you need to update any entry
- When you need to delete any entry


# Built-in actions API

|  Property |  Description | Type  |
|:---|:---|:---|
| **text***  | display name of the action | `string`  |
| **type***  | a string that uniquely identifies the operation on the widget: <ul><li>create</li><li>save</li><li>delete</li></ui> | `OperationType`  |
| **scope***  | a string that uniquely identifies scope of the action: <ul><li>bc</li><li>record</li><li>page</li><li>associate</li></ui>| `OperationScope` |
| **showOnlyIcon***  | the operation button will be displayed as a single icon, without a caption | `boolean` |
| **icon**  | the icon that will be displayed in the button operation on the basis `https://ant.design/components/icon/` |  `string` |
| **autoSaveBefore**  | Validate the record for empty "required" fields before API call |  `boolean` |
| **action**  | used to create an actionGroup |  `string` |

\* - required

# Example of actions for List widget
```json 
{
  actions: [
    {
      type: "create",
      text: "Create",
      icon: "plus",
      showOnlyIcon: true,
      scope: "bc",
      autoSaveBefore: false
    },
    {
      type: "save",
      text: "Save",
      icon: "save",
      showOnlyIcon: true,
      scope: "record",
      autoSaveBefore: true
    },
    {
      type: "delete",
      text: "Delete",
      icon: "delete",
      showOnlyIcon: true,
      scope: "record",
      autoSaveBefore: false
    }
  ]
}
```
