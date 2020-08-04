/*-
 * #%L
 * TESLERDOC - Model
 * %%
 * Copyright (C) 2020 Tesler Contributors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package io.tesler.service.impl;

import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dict.TDDictionaryType;
import io.tesler.core.dto.multivalue.MultivalueField;
import io.tesler.core.dto.multivalue.MultivalueFieldSingleValue;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.service.action.Actions;
import io.tesler.dto.MultipleSelectLinkedValuesExDTO;
import io.tesler.entity.*;
import io.tesler.model.core.entity.BaseEntity_;
import io.tesler.service.MultipleSelectLinkedValuesExService;
import io.tesler.service.meta.MultipleSelectLinkedValuesExFieldMetaBuilder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.tesler.core.dict.TDDictionaryType.COUNTRY;
import static io.tesler.core.dict.TDDictionaryType.LANGUAGE;
import static io.tesler.dto.MultipleSelectLinkedValuesExDTO_.countryList;
import static io.tesler.dto.MultipleSelectLinkedValuesExDTO_.languageList;

@Service
public class MultipleSelectLinkedValuesExServiceImpl extends VersionAwareResponseService<MultipleSelectLinkedValuesExDTO, MultipleSelectLinkedValuesEx>
		implements MultipleSelectLinkedValuesExService {
	public MultipleSelectLinkedValuesExServiceImpl() {
		super(MultipleSelectLinkedValuesExDTO.class, MultipleSelectLinkedValuesEx.class, null, MultipleSelectLinkedValuesExFieldMetaBuilder.class);
	}

	@Override
	protected CreateResult<MultipleSelectLinkedValuesExDTO> doCreateEntity(
			MultipleSelectLinkedValuesEx multipleSelectLinkedValuesEx, BusinessComponent businessComponent) {
		baseDAO.save(multipleSelectLinkedValuesEx);
		return new CreateResult<>(entityToDto(businessComponent, multipleSelectLinkedValuesEx));
	}

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

	private <T extends Storage> List<String> getSelectedValues(MultipleSelectLinkedValuesExDTO dto,
			Set<T> currentStorage, TDDictionaryType dictionaryType) {
		List<String> selected;
		if (dictionaryType.equals(COUNTRY)) {
			selected = dto.getCountryList().getValues().stream()
					.map(MultivalueFieldSingleValue::getValue)
					.distinct()
					.collect(Collectors.toList());
		} else {
			selected = dto.getLanguageList().getValues().stream()
					.map(MultivalueFieldSingleValue::getValue)
					.distinct()
					.collect(Collectors.toList());
		}
		if (currentStorage != null) {
			currentStorage.removeIf(storage -> !selected.contains(dictionaryType.lookupValue(storage.getValue())));
			currentStorage.stream().distinct()
					.map(exist -> dictionaryType.lookupValue(exist.getValue()))
					.filter(selected::contains)
					.forEach(selected::remove);
		}
		return selected;
	}

	private <T extends Storage> MultivalueField assignDTO(Set<T> entities, TDDictionaryType dictionaryType) {
		if (entities == null) {
			return new MultivalueField(Collections.emptyList());
		}
		return new MultivalueField(entities.stream().map(entity ->
				new MultivalueFieldSingleValue(entity.getId().toString(),
						dictionaryType.lookupValue(entity.getValue())))
				.collect(Collectors.toList()));
	}

	@Override
	public Actions<MultipleSelectLinkedValuesExDTO> getActions() {
		return Actions.<MultipleSelectLinkedValuesExDTO>builder()
				.create().add()
				.build();
	}
}
