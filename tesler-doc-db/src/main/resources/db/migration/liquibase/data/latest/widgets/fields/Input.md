# Input

Simple one line text control.

# API

| Property | Description  | Type | Default
|:---|:---|:---|:---|
| **autoFocus** | focus an element when it mounts | `boolean` | undefined |
| **backgroundColor** | color of background | `string` | undefined |
| **className** | component class name | `string` | undefined |
| **disabled** | whether the input is disabled | `boolean` | undefined |
| **onBlur*** | called when blur | `function()` | - |
| **onChange*** | called when value of input is changed | `function(event)` | - |
| **onDrillDown** | called when user click link of input | `function()` | undefined |
| **readOnly** | whether the input is read only | `boolean` | undefined |
| **value*** | input value | `string` | - |

---
\* - required

## Example for using

Add to entity:
```java
public class Bank extends BaseEntity {
    // <...>

    @Column
    private String testInput;
}
```

Add to DTO class and DTO constructor:
```java
public class BankDTO extends DataResponseDTO {
    // <...>

    @SearchParameter
    private String testInput;

    public BankDTO(Bank bank) {
        // <...>

        this.testInput = bank.getTestInput();
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
            testInput
        );
    }
}
```

Set specific fields in field meta:

| Field | Value |
|:---|:---|
| **type*** | `input` |

\* - required

```json
{
  "title": "Test Input",
  "key": "testInput",
  "type": "input"
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
        fields.setPlaceholder(testInput, "PlaceholderInput test");
    }
}
