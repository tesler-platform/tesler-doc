# Group Action

The formation of action groups of widgets.

## When to use
- If you need to make a list of actions to interact with the widget.

## How to use
You need use the parameter `operations.actionGroups` in Widget data. This parameter includes two arrays, `include` and `exclude`, which contain operation types. For a complete list of available operation types consist in `RowMeta`

# Action API in RowMeta

|  Property |  Description | Type  |
|:---|:---|:---|
| **text***  | display name of the action | `string`  |
| **type***  | a string that uniquely identifies the operation on the widget: <ul><li>create</li><li>save</li><li>delete</li><li>associate</li><li>cancel-create</li></ui> | `OperationType`  |
| **scope***  | a string that uniquely identifies scope of the action: <ul><li>bc</li><li>record</li><li>page</li><li>associate</li></ui>| `OperationScope` |
| **showOnlyIcon***  | the operation button will be displayed as a single icon, without a caption | `boolean` |
| **icon**  | the icon that will be displayed in the button operation on the basis `https://ant.design/components/icon/` |  `string` |
| **autoSaveBefore**  | Validate the record for empty "required" fields before API call |  `boolean` |
| **action**  | used to create an actionGroup |  `string` |

\* - required


# Example of actions in RowMeta
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

# Example of actionGroup for widget
```json 
{
    options: {
        actionGroups: {
            include: [
                "save",
            ],
            exclude: [
                "create",
                "delete"
            ]
        },
    }
}
```