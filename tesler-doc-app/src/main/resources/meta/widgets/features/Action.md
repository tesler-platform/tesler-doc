# Action

Various types of actions are used to interact with the widget.

## When to use
- When you need to create, save, or delete an entry from widget.

## How to use
You need to pass the `rowMeta` parameter to the `View` level that contains an array with actions.

# Action API

|  Property |  Description | Type  |
|:---|:---|:---|
| **text***  | display name of the action | `string`  |
| **type***  | a string that uniquely identifies the operation on the widget: <ul><li>create</li><li>save</li><li>delete</li><li>associate</li><li>cancel-create</li></ui> | `OperationType`  |
| **scope***  | a string that uniquely identifies scope of the action: <ul><li>bc</li><li>record</li><li>page</li><li>associate</li></ui>| `OperationScope` |
| **showOnlyIcon***  | the operation button will be displayed as a single icon, without a caption | `boolean` |
| **icon**  | the icon that will be displayed in the button operation on the basis `https://ant.design/components/icon/` |  `string` |
| **autoSaveBefore**  | Validate the record for empty "required" fields before API call |  `boolean` |
| **action**  | used to create an actionGroup |  `string` |


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


if you use the `associate` action, a modal window will be opened. In this window, you can insert another widget with your own operations.
```json 
{
    type:"associate"
    text:"add"
    icon:"plus"
    showOnlyIcon:true
    scope:"bc"
    autoSaveBefore:true
}
```

In case of two Assocs on the same parent business component assoc's name should be passed from backend as follows: 

```json 
 Actions.<TestResponseDto>builder()
				.associate(bcExample)
				.withoutIcon()
				.withoutAutoSaveBefore()
				.add()
				.build();

```
 Otherwise, Assoc is identified by parent business component and can not be distinguished. 