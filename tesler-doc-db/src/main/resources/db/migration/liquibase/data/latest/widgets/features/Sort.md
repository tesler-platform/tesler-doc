## Sort
The system has the ability to sort recording in the field. Sort operation allows display records in the specified row order.

### Sort widget:

It is possible to display the necessary sorters on a specific widget immediately after loading the view. Currently, such sorters cannot be removed from the widget's panel.
After adding a sort to the widget field, a sorter object is created in screen meta.

#### BcMeta

Object format:

```ts
bcName: "bankDoc",
sort: {
    fieldName: "name",
    direction: "asc"
}
```

Fields description:

| Property | Description  | Type
|:---|:---|:---|
| **bcName** | name of Business Component  | `string`
| **filter** | sort objects | `object[]`
| **fieldName** | sort field widget | `string`
| **direction** | direction sort  | `asc`, `desc`


---

According the data field type, sort can be applied for certain fields column types. Sorting is not available on fields of other types.

Sort available field types:
* number
* input
* date
* dateTime
* dateTimeWithSeconds
* dictionary
* text
* percent
* money
* checkbox
* pickList
