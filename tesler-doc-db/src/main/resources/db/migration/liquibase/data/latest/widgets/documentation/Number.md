# Number

To display numbers, fields of the types: `number`, `percent`, `money` are used.

# API

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

---
\* - required

## Example for using

Add to entity:
```java
public class Bank extends BaseEntity {
    // <...>

    @Column
    private Integer activeProjectsAmount;

    @Column
    private Long testPercent;

    @Column
    private Double testMoney;
}
```

Add to DTO class and DTO constructor:
```java
public class BankDTO extends DataResponseDTO {
    // <...>

    @SearchParameter
    private Integer activeProjectsAmount;

    @SearchParameter
    private Long testPercent;

    @SearchParameter
    private Double testMoney;

    public BankDTO(Bank bank) {
        // <...>

        this.activeProjectsAmount = bank.getActiveProjectsAmount();
        this.testPercent = bank.getTestPercent();
        this.testMoney = bank.getTestMoney();
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
            activeProjectsAmount,
            testPercent,
            testMoney
        );
    }
}
```

Set specific fields in field meta:

| Field | Value |
|:---|:---|
| **type*** | `number` \| `percent` \| `money` |

\* - required

```json
{
  "title": "Number",
  "key": "activeProjectsAmount",
  "type": "number"
}
```

```json
{
  "title": "Percent",
  "key": "testPercent",
  "type": "percent"
}
```

```json
{
  "title": "Money",
  "key": "testMoney",
  "type": "money"
}
```