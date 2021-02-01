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

import static io.tesler.dto.BankDTO_.activeProjectsAmount;
import static io.tesler.dto.BankDTO_.country;
import static io.tesler.dto.BankDTO_.isNational;
import static io.tesler.dto.BankDTO_.name;
import static io.tesler.dto.BankDTO_.notes;
import static io.tesler.dto.BankDTO_.size;
import static io.tesler.dto.BankDTO_.testPercent;
import static io.tesler.dto.BankDTO_.testMoney;
import static io.tesler.dto.BankDTO_.testDate;
import static io.tesler.dto.BankDTO_.testInput;
import static io.tesler.dto.BankDTO_.testPickList;
import static io.tesler.dto.BankDTO_.testDictionary;
import static io.tesler.dto.BankDTO_.testRadio;

import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dict.TDDictionaryType;
import io.tesler.core.dto.DrillDownType;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import io.tesler.crudma.config.TESLERDOCServiceAssociation;
import io.tesler.dto.BankDTO;
import org.springframework.stereotype.Service;

@Service
public class BankFieldMetaBuilder extends FieldMetaBuilder<BankDTO> {

	@Override
	public void buildRowDependentMeta(RowDependentFieldsMeta<BankDTO> fields,
			InnerBcDescription innerBcDescription, Long rowId, Long parRowId) {
		fields.setEnabled(
				name,
				activeProjectsAmount,
				isNational,
				size,
				notes,
				country,
				testPercent,
				testMoney,
				testDate,
				testInput,
				testPickList,
				testDictionary,
				testRadio
		);
		fields.setRequired(name);
		fields.setDictionaryTypeWithAllValues(country, TDDictionaryType.COUNTRY);
		fields.setDictionaryTypeWithAllValues(testDictionary, TDDictionaryType.DOC_TEST);
		fields.setDictionaryTypeWithAllValues(testRadio, TDDictionaryType.DOC_TEST);
		fields.setDictionaryTypeWithAllValues(size, TDDictionaryType.SIZE);
		fields.setPlaceholder(testInput, "PlaceholderInput test");
		fields.setPlaceholder(testDate, "PlaceholderDate test");
		if (innerBcDescription.getName().toLowerCase().equals("bankdoc")) {
			fields.setDrilldown(
					name,
					DrillDownType.INNER,
					"/screen/components/view/form/" + TESLERDOCServiceAssociation.bankDoc + "/" + rowId
			);
		} else {
			fields.setDrilldown(
					name,
					DrillDownType.INNER,
					"/screen/example/view/bankcard/" + TESLERDOCServiceAssociation.bank + "/" + rowId
			);
		}

	}

	@Override
	public void buildIndependentMeta(FieldsMeta<BankDTO> fieldsMeta, InnerBcDescription innerBcDescription, Long aLong) {
		fieldsMeta.enableFilter(
				name,
				activeProjectsAmount,
				size,
				notes,
				country,
				testPercent,
				testMoney,
				testDate
		);
		fieldsMeta.setAllFilterValuesByLovType(country, TDDictionaryType.COUNTRY);
		fieldsMeta.setAllFilterValuesByLovType(size, TDDictionaryType.SIZE);
	}

}
