# Radio

Allows to select a single state from multiple options (display of the dictionary items like radio buttons).

# API

| Property | Description  | Type | Default |
|:---|:---|:---|:---|
| **backgroundColor** | color of background | `string` | undefined |
| **className** | component class name | `string` | undefined |
| **disabled** | whether the input is disabled | `boolean` | undefined |
| **onChange** | called when value of input is change | `function(value)` | undefined |
| **onDrillDown** | called when user click link of input | `function()` | undefined |
| **readOnly** | whether the input is read only | `boolean` | undefined |
| **value** | input value | `string` \| `null` | undefined |
| **values*** | list of dropdown values to select from | `DictionaryValuesItem[]` | - |

#### DictionaryValuesItem
| Property | Description  | Type | Default |
|:---|:---|:---|:---|
| **value*** | item value | `string` | - |
| **icon** | space separated icon parameters: `antd icon code`, `css color code` | `string` | undefined |

---
\* - required

## Example for using

Add to entity:
```java
public class Bank extends BaseEntity {
    // <...>

    @Column
    private LOV testRadio;
}
```

Add to DTO class and DTO constructor:
```java
public class BankDTO extends DataResponseDTO {
    // <...>

    @SearchParameter(type = LOV)
    @TDLov(TDDictionaryType.DOC_TEST)
    private String testRadio;

    public BankDTO(Bank bank) {
        // <...>
        this.testRadio = TDDictionaryType.DOC_TEST.lookupValue(bank.getTestRadio());
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
            testRadio
        );

        // <...>
        fields.setDictionaryTypeWithAllValues(testRadio, TDDictionaryType.DOC_TEST);
    }
}
```


Set specific fields in field meta:

| Field | Value |
|:---|:---|
| **type*** | `radio` |

\* - required

```json
{
  "title" : "Radio",
  "key" : "testRadio",
  "type" : "radio"
}
```
