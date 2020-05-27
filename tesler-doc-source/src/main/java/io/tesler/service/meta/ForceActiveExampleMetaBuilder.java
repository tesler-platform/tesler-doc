/*-
 * #%L
 * TESLERDOC - Source
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
import io.tesler.core.dao.BaseDAO;
import io.tesler.core.dict.Dictionaries;
import io.tesler.core.dict.TDDictionaryType;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import io.tesler.dto.ForceActiveExampleDTO;
import io.tesler.entity.ForceActiveExample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.tesler.dto.ForceActiveExampleDTO_.country;
import static io.tesler.dto.ForceActiveExampleDTO_.language;

@Service
@RequiredArgsConstructor
public class ForceActiveExampleMetaBuilder extends FieldMetaBuilder<ForceActiveExampleDTO> {

	private final BaseDAO baseDAO;

	@Override
	public void buildRowDependentMeta(RowDependentFieldsMeta<ForceActiveExampleDTO> fields, InnerBcDescription bcDescription, Long rowId, Long parentId) {
		fields.setEnabled(country);
		fields.setDictionaryTypeWithAllValues(country, TDDictionaryType.COUNTRY);
		ForceActiveExample exampleEntity = Optional.ofNullable(rowId)
				.map(id -> baseDAO.findById(ForceActiveExample.class, id))
				.orElse(null);
		LOV countryLov = exampleEntity.getCountry();
		List<LOV> languages = getLanguagesByCountry(countryLov);
		fields.setDictionaryTypeWithConcreteValues(language, TDDictionaryType.LANGUAGE, languages);
		if (!languages.isEmpty()) {
			fields.setEnabled(language);
		}
	}

	@Override
	public void buildIndependentMeta(FieldsMeta<ForceActiveExampleDTO> fields, InnerBcDescription bcDescription, Long parentId) {
		fields.setEnabled(country);
		fields.setDictionaryTypeWithAllValues(country, TDDictionaryType.COUNTRY);
		fields.setForceActive(country);
	}

	private List<LOV> getLanguagesByCountry(LOV countryLov) {
		if (Dictionaries.Country.GERMANY.equals(countryLov)) {
			return Arrays.asList(
					Dictionaries.Language.GERMAN,
					Dictionaries.Language.LOW_GERMAN,
					Dictionaries.Language.DANISH,
					Dictionaries.Language.FRISIAN,
					Dictionaries.Language.ROMANY,
					Dictionaries.Language.SORBIAN
			);
		}
		if (Dictionaries.Country.UK.equals(countryLov)) {
			return Arrays.asList(
					Dictionaries.Language.ENGLISH,
					Dictionaries.Language.SCOTS,
					Dictionaries.Language.WELSH
			);
		}
		if (Dictionaries.Country.RUSSIA.equals(countryLov)) {
			return Arrays.asList(
					Dictionaries.Language.RUSSIAN,
					Dictionaries.Language.UKRAINIAN,
					Dictionaries.Language.UDMURT,
					Dictionaries.Language.BURYAT
			);
		}
		return new ArrayList<>();
	}
}
