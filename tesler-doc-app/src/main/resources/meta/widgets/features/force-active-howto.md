## How to use

You need to call a [setForceActive](https://github.com/tesler-platform/tesler/blob/master/tesler-core/src/main/java/io/tesler/core/dto/rowmeta/FieldsMeta.java#L77) method on the `fieldsMeta` argument of [`buildIndependentMeta`](https://github.com/tesler-platform/tesler/blob/master/tesler-core/src/main/java/io/tesler/core/service/rowmeta/FieldMetaBuilder.java#L52) method implementation for your service meta builder:

```java
// ...
import static io.tesler.dto.ForceActiveExampleDTO_.country;
import static io.tesler.dto.ForceActiveExampleDTO_.language;

@Service
@RequiredArgsConstructor
public class ForceActiveExampleMetaBuilder extends FieldMetaBuilder<ForceActiveExampleDTO> {

  // ...

  @Override
  public void buildIndependentMeta(FieldsMeta<ForceActiveExampleDTO> fields, InnerBcDescription bcDescription, Long parentId) {
    fields.setEnabled(country);
    fields.setAllFilterValuesByLovType(country, TDDictionaryType.COUNTRY);
    fields.setForceActive(country); // Mark the `country` field as force active
  }

  // ...

}
```

After that, Tesler UI will understand that any change to the `country` field should fetch an updated row meta via `preview save` call. The logic of the `language` field can be customized to reflect changes of `country` field:

```java
@Service
@RequiredArgsConstructor
public class ForceActiveExampleMetaBuilder extends FieldMetaBuilder<ForceActiveExampleDTO> {

  private final BaseDAO baseDAO;

  // ...

  @Override
  public void buildRowDependentMeta(RowDependentFieldsMeta<ForceActiveExampleDTO> fields, InnerBcDescription bcDescription, Long rowId, Long parentId) {
    // Get record
    ForceActiveExample exampleEntity = baseDAO.findById(ForceActiveExample.class, rowId);
    // Get updated `country` value
    LOV countryLov = exampleEntity.getCountry();
    // Get available languages for selected `country` value based on some custom logic
    List<LOV> languages = getLanguagesByCountry(countryLov);
    // Set them for `language` field
    fields.setDictionaryTypeWithConcreteValues(language, TDDictionaryType.LANGUAGE, languages);
    // If no languages available, disable field
    if (!languages.isEmpty()) {
      fields.setEnabled(language);
    }
  }
}

```
