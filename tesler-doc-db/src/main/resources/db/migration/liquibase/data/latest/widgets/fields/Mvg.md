# Multivalue
With `multivalue` field is a field that can contain more than one value.

## API
| Property | Description | Type | Default |
|:---|:---| :---| :---|
| **widgetName*** | name of widget containing mvg-field | `string` | - |
| **bcName*** | business component name | `string` | - |
| **disabled** | whether input is disabled | `boolean` | undefined |
| **placeholder** | placeholder for mvg-field | `string` | - |
| **fieldKey** | name of mvg-field | `string` | - |
| **fields** | fields that should be converted into mvg | `string` | - |

## Example for using
Adding to entity:
```java
public class Ware extends BaseEntity {
//<...>
    @OneToMany
    Set<WareComponent> componentSet;

    @OneToMany
    List<WareComponent> componentList;
}
```
Adding to DTO:
```java
public class WareDTO extends DataResponseDTO {
//<...>
    private MultivalueField components;

    private MultivalueField componentsList;
//<...>
}
```
Converting to Multivalue in DTO constructor:
```java
public class WareDTO extends DataResponseDTO {

    public WareDTO(Ware ware) {

            if (ware.getComponentSet() != null) {
                this.components = ware.getComponentSet().stream().peek(wc -> {
                    wc.getName();
                }).collect(MultivalueField.toMultivalueField(
                        wc -> wc.getName(), WareComponent::getName
                ));
            }
            //Or like this from List
            List<MultivalueFieldSingleValue> comps = new ArrayList<>();
            if (ware.getComponentList() != null) {
                comps = ware.getComponentList().stream().map(wc -> new MultivalueFieldSingleValue(wc.getId().toString(), wc.getName()))
                        .collect(Collectors.toList());
            }
            this.componentsList = new MultivalueField(comps);
    
        }
}
```
Set specific fields in field meta:

| Field | Value |
|:---|:---|
| **type*** | `multivalue` |

\* - required
```ts
    {
      "label": "Components",
      "key": "components",
      "type": "multivalue"
    }
```
```ts
    {
      "label": "Components List",
      "key": "componentsList",
      "type": "multivalue"
    }
```

## Example
