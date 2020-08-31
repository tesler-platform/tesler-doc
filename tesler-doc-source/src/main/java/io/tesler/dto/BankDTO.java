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

package io.tesler.dto;

import io.tesler.api.data.dto.DataResponseDTO;
import io.tesler.core.dict.TDDictionaryType;
import io.tesler.core.util.filter.SearchParameter;
import io.tesler.core.util.filter.provider.impl.DateValueProvider;
import io.tesler.core.util.filter.provider.impl.LovValueProvider;
import io.tesler.entity.Bank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BankDTO extends DataResponseDTO {

	@SearchParameter
	private String name;

	@SearchParameter
	private Integer activeProjectsAmount;

	@SearchParameter
	private Boolean isNational;

	@SearchParameter
	private String size;

	@SearchParameter
	private String notes;

	@SearchParameter
	private String country;

	@SearchParameter
	private String testInput;

	@SearchParameter
	private String testPickList;

	@SearchParameter(provider = LovValueProvider.class)
	@TDLov(TDDictionaryType.DOC_TEST)
	private String testDictionary;

	@SearchParameter(provider = LovValueProvider.class)
	@TDLov(TDDictionaryType.DOC_TEST)
	private String testRadio;

	@SearchParameter
	private Long testPercent;

	@SearchParameter
	private Double testMoney;

	@SearchParameter(provider = DateValueProvider.class)
	private LocalDateTime testDate;

	public BankDTO(Bank bank) {
		this.id = bank.getId().toString();
		this.name = bank.getName();
		this.activeProjectsAmount = bank.getActiveProjectsAmount();
		this.isNational = bank.isNational();
		this.size = TDDictionaryType.COUNTRY.lookupValue(bank.getCountry());
		this.notes = bank.getNotes();
		this.country = TDDictionaryType.COUNTRY.lookupValue(bank.getCountry());
		this.testPercent = bank.getTestPercent();
		this.testMoney = bank.getTestMoney();
		this.testDate = bank.getTestDate();
		this.testInput = bank.getTestInput();
		this.testPickList = bank.getTestPickList();
		this.testDictionary = TDDictionaryType.DOC_TEST.lookupValue(bank.getTestDictionary());
		this.testRadio = TDDictionaryType.DOC_TEST.lookupValue(bank.getTestRadio());
	}

}
