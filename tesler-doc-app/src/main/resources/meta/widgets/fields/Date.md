# Date

To display dates, fields of the types: `date`, `dateTime`, `dateTimeWithSeconds` are used.

# API

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

Add to entity:
```java
public class Bank extends BaseEntity {
    // <...>

    @Column
    private LocalDateTime testDate;
}
```

Add to DTO class and DTO constructor:
```java
public class BankDTO extends DataResponseDTO {
    // <...>

    @SearchParameter
    private LocalDateTime testDate;

    public BankDTO(Bank bank) {
        // <...>

        this.testDate = bank.getTestDate();
    }
}
```

Add to field meta builder:

```java
@Service
public class BankFieldMetaBuilder extends FieldMetaBuilder<BankDTO> {
    @Override
    public void buildRowDependentMeta( /*<...>*/ ) {
        fields.setEnabled(
            // <...>
            testDate
        );
    }
}
```

Set specific fields in field meta:

| Field | Value |
|:---|:---|
| **type*** | `date` \| `dateTime` \| `dateTimeWithSeconds` |

\* - required

```json
{
  "title": "Date",
  "key": "testDate",
  "type": "date"
}
```
```json
{
  "title": "Date and Time",
  "key": "testDate",
  "type": "dateTime"
}
```
```json
{
  "title": "Date and Time (with seconds)",
  "key": "testDate",
  "type": "dateTimeWithSeconds"
}
```

### Placeholder

Allows you to specify a hint to fill in the field.

#### Example for using

Add to field meta builder:

```java
@Service
public class BankFieldMetaBuilder extends FieldMetaBuilder<BankDTO> {
    @Override
    public void buildRowDependentMeta( /*<...>*/ ) {
        // <...>
        fields.setPlaceholder(testDate, "placeholderDate test");
    }
}
