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

package io.tesler.service.impl;

import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dict.TDDictionaryType;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.service.action.Actions;
import io.tesler.dto.ForceActiveExampleDTO;
import io.tesler.entity.ForceActiveExample;
import io.tesler.service.ForceActiveExampleService;
import io.tesler.service.meta.ForceActiveExampleMetaBuilder;
import org.springframework.stereotype.Service;

import static io.tesler.dto.ForceActiveExampleDTO_.country;
import static io.tesler.dto.ForceActiveExampleDTO_.language;

@Service
public class ForceActiveExampleServiceImpl
		extends VersionAwareResponseService<ForceActiveExampleDTO, ForceActiveExample>
		implements ForceActiveExampleService {

	public ForceActiveExampleServiceImpl() {
		super(ForceActiveExampleDTO.class, ForceActiveExample.class, null, ForceActiveExampleMetaBuilder.class);
	}

	@Override
	protected CreateResult<ForceActiveExampleDTO> doCreateEntity(ForceActiveExample entity, BusinessComponent bc) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected ActionResultDTO<ForceActiveExampleDTO> doUpdateEntity(ForceActiveExample entity, ForceActiveExampleDTO data, BusinessComponent bc) {
		if (data.hasChangedFields()) {
			if (data.isFieldChanged(country)) {
				entity.setCountry(TDDictionaryType.COUNTRY.lookupName(data.getCountry()));
			}
			if (data.isFieldChanged(language)) {
				entity.setLanguage(TDDictionaryType.LANGUAGE.lookupName(data.getLanguage()));
			}
		}
		return new ActionResultDTO<>(entityToDto(bc, entity));
	}

	@Override
	public Actions<ForceActiveExampleDTO> getActions() {
		return Actions.<ForceActiveExampleDTO>builder()
				.save().add()
				.build();
	}
}
