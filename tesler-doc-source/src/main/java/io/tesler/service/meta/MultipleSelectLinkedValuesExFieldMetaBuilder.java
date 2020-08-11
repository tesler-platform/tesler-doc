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

package io.tesler.service.meta;

import io.tesler.api.data.dictionary.LOV;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.multivalue.MultivalueField;
import io.tesler.core.dto.multivalue.MultivalueFieldSingleValue;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import io.tesler.dto.MultipleSelectLinkedValuesExDTO;
import io.tesler.entity.LinkedValuesHolderEx;
import io.tesler.entity.LinkedValuesHolderEx_;
import io.tesler.model.core.dao.JpaDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.tesler.core.dict.Dictionaries.Country.*;
import static io.tesler.core.dict.TDDictionaryType.COUNTRY;
import static io.tesler.core.dict.TDDictionaryType.LANGUAGE;
import static io.tesler.dto.MultipleSelectLinkedValuesExDTO_.countryList;
import static io.tesler.dto.MultipleSelectLinkedValuesExDTO_.languageList;

@Service
@RequiredArgsConstructor
public class MultipleSelectLinkedValuesExFieldMetaBuilder extends FieldMetaBuilder<MultipleSelectLinkedValuesExDTO> {

	private final JpaDao jpaDao;

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
		fieldsMeta.setDictionaryTypeWithConcreteValues(countryList, COUNTRY, RUSSIA, BRAZIL, CANADA);
		fieldsMeta.setPlaceholder(countryList, "Select countries");
		fieldsMeta.setPlaceholder(languageList, "Select languages");

	}

	@Override
	public void buildIndependentMeta(FieldsMeta<MultipleSelectLinkedValuesExDTO> fieldsMeta,
			InnerBcDescription innerBcDescription, Long aLong) {
		fieldsMeta.setForceActive(countryList);

	}
}
