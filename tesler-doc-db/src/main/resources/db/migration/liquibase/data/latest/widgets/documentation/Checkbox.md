# Checkbox

To display checkboxes, fields of the type `checkbox` are used.

# API

| Property | Description  | Type | Default
|:---|:---|:---|:---|
| **fieldName*** | the key name of the checkbox  | `string`  | - |
| **fieldLabel*** | the name of the checkbox | `string`  | - |
| **bcName*** | the name of the current business component  | `string`  | - |
| **cursor*** | currently active record | `string`  | - |
| **value*** | the checkbox content value | `string` \| `number` \| `boolean` \| `null` \| `[id: string, value: string]` \| `undefined`  | - |
| **readOnly** | whether the checkbox is read only  | `boolean`  | undefined |

---
\* - required

## Example for using

Add to entity:
```java
public class Bank extends BaseEntity {
    // <...>

    @Column
    private boolean isNational;
}
```

Add to DTO class and DTO constructor:
```java
public class BankDTO extends DataResponseDTO {
    // <...>

    @SearchParameter
    private Boolean isNational;

    public BankDTO(Bank bank) {
        // <...>

        this.isNational = bank.isNational();
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
            isNational
        );
    }
}
```

Set specific fields in field meta:

| Field | Value |
|:---|:---|
| **type*** | `checkbox` |

\* - required

```json
{
  "title": "Checkbox",
  "key": "isNational",
  "type": "checkbox"
}
```