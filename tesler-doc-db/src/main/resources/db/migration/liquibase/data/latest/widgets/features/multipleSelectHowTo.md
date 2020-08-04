## How to use
To use `multpleSelect` field you have to map a `MultiValueField` field key from DTO to your `*.widget.json` file.
```json
// ...
"fields": [
    {
      "title": "Country",
      "key": "countryList",
      "type": "multipleSelect"
    },
    {
      "title": "Language",
      "key": "languageList",
      "type": "multipleSelect"
    }
  ]
// ...
```
After that you have to specify overrides for `#doUpdateEntity` and `#entityToDto` methods in the response service.<br>
**_Alternative way_**<br/>
You can `@Override` [doPreview](https://github.com/tesler-platform/tesler/blob/651249416cc575b661987c5236f1b3db084eedf7/tesler-core/src/main/java/io/tesler/core/crudma/impl/VersionAwareResponseService.java#L112) to build a response DTO in a different way.
 

```java
// ...
import static io.tesler.core.dict.TDDictionaryType.COUNTRY;
import static io.tesler.core.dict.TDDictionaryType.LANGUAGE;
import static io.tesler.dto.MultipleSelectLinkedValuesExDTO_.countryList;
import static io.tesler.dto.MultipleSelectLinkedValuesExDTO_.languageList;

@Service
public class MultipleSelectLinkedValuesExServiceImpl extends VersionAwareResponseService<MultipleSelectLinkedValuesExDTO, MultipleSelectLinkedValuesEx>
		implements MultipleSelectLinkedValuesExService {

  // ...
 @Override
 	protected ActionResultDTO<MultipleSelectLinkedValuesExDTO> doUpdateEntity(MultipleSelectLinkedValuesEx entity,
 			MultipleSelectLinkedValuesExDTO data, BusinessComponent bc) {
 		if (data.hasChangedFields()) {
 			if (data.isFieldChanged(countryList)) {
 				Set<MultipleSelectExCountryStorage> countries = entity.getCountries();
 				List<String> selectedCountries = this.getSelectedValues(data, countries, COUNTRY);
 
 				selectedCountries.forEach(selectedCountryName -> {
 					MultipleSelectExCountryStorage country = new MultipleSelectExCountryStorage();
 					country.setValue(TDDictionaryType.COUNTRY.lookupName(selectedCountryName));
 					country.setParentEntity(entity);
 					baseDAO.save(country);
 				});
 			}
 			if (data.isFieldChanged(languageList)) {
 				Set<MultipleSelectExLanguageStorage> languages = entity.getLanguages();
 				List<String> selectedCountries = this.getSelectedValues(data, languages, LANGUAGE);
 
 				selectedCountries.forEach(selectedLanguageName -> {
 					MultipleSelectExLanguageStorage language = new MultipleSelectExLanguageStorage();
 					language.setValue(LANGUAGE.lookupName(selectedLanguageName));
 					language.setParentEntity(entity);
 					baseDAO.save(language);
 				});
 			}
 		}
 		return new ActionResultDTO<>(entityToDto(bc, entity));
 	}
 
 	@Override
 	protected MultipleSelectLinkedValuesExDTO entityToDto(BusinessComponent bc, MultipleSelectLinkedValuesEx entity) {
 		MultipleSelectLinkedValuesExDTO dto = super.entityToDto(bc, entity);
 		Set<MultipleSelectExCountryStorage> countries = baseDAO.getStream(MultipleSelectExCountryStorage.class,
 				(root, cq, cb) -> cb.equal(root
 						.get(MultipleSelectExCountryStorage_.parentEntity)
 						.get(BaseEntity_.id), entity.getId()))
 				.collect(Collectors.toSet());
 		dto.setCountryList(assignDTO(countries, COUNTRY));
 
 		Set<MultipleSelectExLanguageStorage> languages = baseDAO.getStream(MultipleSelectExLanguageStorage.class,
 				(root, cq, cb) -> cb.equal(root
 						.get(MultipleSelectExLanguageStorage_.parentEntity)
 						.get(BaseEntity_.id), entity.getId()))
 				.collect(Collectors.toSet());
 		dto.setLanguageList(assignDTO(languages, LANGUAGE));
 		return dto;
 	}
  // ...

}
```

The final step is to build a `RowMeta` for a DTO in way you need.<br/> In the example a separate database table is used to store dependencies between country and language. 

```java
// ...
import static io.tesler.core.dict.TDDictionaryType.COUNTRY;
import static io.tesler.core.dict.TDDictionaryType.LANGUAGE;
import static io.tesler.dto.MultipleSelectLinkedValuesExDTO_.countryList;
import static io.tesler.dto.MultipleSelectLinkedValuesExDTO_.languageList;

@Service
@RequiredArgsConstructor
public class MultipleSelectLinkedValuesExFieldMetaBuilder extends FieldMetaBuilder<MultipleSelectLinkedValuesExDTO> {

	private final JpaDao jpaDao;

    // ...

  @Override
  	public void buildRowDependentMeta(RowDependentFieldsMeta<MultipleSelectLinkedValuesExDTO> fieldsMeta,
  			InnerBcDescription innerBcDescription, Long aLong, Long aLong1) {
  		
  		List<MultivalueFieldSingleValue> countries = ((MultivalueField) fieldsMeta.get(countryList.getName()).getCurrentValue())
  				.getValues();
  		if (countries.isEmpty()) {
  			fieldsMeta.setDictionaryTypeWithAllValues(languageList, LANGUAGE);
  		} else {
  			List<LOV> languageDictionaryValues = countries.stream()
  					.map(MultivalueFieldSingleValue::getValue)
  					.map(value -> {
  						LOV dictValue = COUNTRY.lookupName(value);
  						return jpaDao.getStream(LinkedValuesHolderEx.class,
  								(root, cq, cb) ->
  										cb.equal(root.get(LinkedValuesHolderEx_.country), dictValue)
  						)
  								.map(LinkedValuesHolderEx::getLanguage);
  					})
  					.reduce(Stream::concat).orElse(Stream.empty())
  					.distinct()
  					.sorted(Comparator.comparing(LANGUAGE::lookupValue))
  					.collect(Collectors.toList());
  			fieldsMeta.setDictionaryTypeWithConcreteValuesFromList(languageList, LANGUAGE, languageDictionaryValues);
  		}
  		fieldsMeta.setEnabled(languageList, countryList);
  	}
    // ...
}
```
Also do not forget to make a field `forceActive`:
```java
 // ...
 import static io.tesler.core.dict.TDDictionaryType.COUNTRY;
 import static io.tesler.core.dict.TDDictionaryType.LANGUAGE;
 import static io.tesler.dto.MultipleSelectLinkedValuesExDTO_.countryList;
 import static io.tesler.dto.MultipleSelectLinkedValuesExDTO_.languageList;
 
 @Service
 @RequiredArgsConstructor
 public class MultipleSelectLinkedValuesExFieldMetaBuilder extends FieldMetaBuilder<MultipleSelectLinkedValuesExDTO> {
 
        // ...
  
    @Override
    	public void buildIndependentMeta(FieldsMeta<MultipleSelectLinkedValuesExDTO> fieldsMeta,
    			InnerBcDescription innerBcDescription, Long aLong) {
    		fieldsMeta.setForceActive(countryList);
    	}
  
        // ...
  
  }
```