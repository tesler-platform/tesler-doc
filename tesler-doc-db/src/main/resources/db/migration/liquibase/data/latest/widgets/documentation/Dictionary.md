# Dictionary

Allows to select value from dropdown list of predefined values.

# API

| Property | Description  | Type | Default |
|:---|:---|:---|:---|
| **backgroundColor** | color of background | `string` | undefined |
| **className** | component class name | `string` | undefined |
| **disabled** | whether the input is disabled | `boolean` | undefined |
| **fieldName*** | field key | `string` | - |
| **metaIcon** | icon | `ReactNode` | undefined |
| **onChange** | called when value of input is change | `function(value)` | undefined |
| **onDrillDown** | called when user click link of input | `function()` | undefined |
| **popupContainer** | parent Node which the selector should be rendered to | `HTMLElement` | undefined |
| **readOnly** | whether the input is read only | `boolean` | undefined |
| **value** | input value | `string` \| `null` | undefined |
| **valueIcon** | space separated icon parameters: `antd icon code`, `css color code` | `string` | undefined |
| **values*** | list of dropdown values to select from | `DictionaryValuesItem[]` | undefined |

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
    private LOV testDictionary;
}
```

Add to DTO class and DTO constructor:
```java
public class BankDTO extends DataResponseDTO {
    // <...>

    @SearchParameter(type = LOV)
    @TDLov(TDDictionaryType.DOC_TEST)
    private String testDictionary;

    public BankDTO(Bank bank) {
        // <...>
        this.testDictionary = TDDictionaryType.DOC_TEST.lookupValue(bank.getTestDictionary());
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
            testDictionary
        );

        // <...>
        fields.setDictionaryTypeWithAllValues(testDictionary, TDDictionaryType.DOC_TEST);
    }
}
```


Set specific fields in field meta:

| Field | Value |
|:---|:---|
| **type*** | `dictionary` |

\* - required

```json
{
  "title" : "Dictionary",
  "key" : "testDictionary",
  "type" : "dictionary"
}
```