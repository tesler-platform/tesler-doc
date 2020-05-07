# Picklist

Control that allows to select value from popup list widget.

# API

| Property | Description  | Type | Default |
|:---|:---|:---|:---|
| **backgroundColor** | color of background | `string` | undefined |
| **bcName*** | name of popup BC | `string` | - |
| **className** | component class name | `string` | undefined |
| **cursor*** | active record id | `string` | - |
| **disabled** | whether the input is disabled | `boolean` | undefined |
| **parentBCName*** | name of field BC | `string` | - |
| **onChange** | called when value of input is change | `function(event)` | undefined |
| **onDrillDown** | called when user click link of input | `function()` | undefined |
| **pickMap*** | object that describes from which data fields we take values and to which data fields we should insert them | `PickMap` | - |
| **readOnly** | whether the input is read only | `boolean` | undefined |
| **value** | input value | `string` | undefined |

---
\* - required

#### PickMap JSON expample
```json
{
  "fieldNameInsertValueInto": "fieldNameGetValueFrom"
}
```

## Example for using

Add to entity:
```java
public class Bank extends BaseEntity {
    // <...>

    @Column
    private String testPickList;
}
```

Add to DTO class and DTO constructor:
```java
public class BankDTO extends DataResponseDTO {
    // <...>

    @SearchParameter
    private String testPickList;

    public BankDTO(Bank bank) {
        // <...>

        this.testPickList = bank.getTestPickList();
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
            testPickList
        );
    }
}
```

Set specific fields in field meta:

| Field | Value |
|:---|:---|
| **type*** | `pickList` |
| **popupBcName*** | Enter the name of a popup List BC |
| **pickMap*** | An object in which a data mapping is stored. Object string values represents field names to get data from, object keys represents field names to put data to. |

\* - required

```json
{
  "title": "Pick List",
  "key": "testPickList",
  "type": "pickList",
  "popupBcName": "bankDocPicklist",
  "pickMap": {
    "testPickList": "name",
    "testInput": "activeProjectsAmount"
  }
}
```