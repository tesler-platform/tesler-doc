## Filter

The system has the ability to filter recording by value in the field. For this, it is necessary to indicate that this field is allowed to filter in FieldMetaBuilder, and also indicate in the DTO the @SearchParameter annotation for the field that is being searched.

### Filter widget:

It is possible to display the necessary filters on a specific widget immediately after loading the view. Currently, such filters cannot be removed from the widget's filter panel.
You can add filters to different fields at the same time. After adding a filter to the widget field, a filter object is created in screen meta.

#### BcMeta

Object format:

```ts
bcName: "bankDoc",
filter: {
    type: "equals",
    fieldName: "name",
    value: "Abc"
}
```

Fields description:

| Property | Description  | Type
|:---|:---|:---|
| **bcName** | name of Business Component  | `string`
| **filter** | filter objects | `object[]`
| **type** | filter operation type  | `FilterType[]`
| **fieldName** | widget field to which the filter is applied  | `string`
| **value** | value for filtering  | `string`, `number`, `boolean`, `null`, `MultivalueSingleValue[]`, `undefined`
