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
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.dto.CustomFieldsExampleDTO;
import io.tesler.entity.CustomFieldsExample;
import io.tesler.service.CustomFieldsExampleService;
import io.tesler.service.meta.CustomFieldsExampleFieldMetaBuilder;
import org.springframework.stereotype.Service;

@Service
public class CustomFieldsExampleServiceImpl
		extends VersionAwareResponseService<CustomFieldsExampleDTO, CustomFieldsExample>
		implements CustomFieldsExampleService {

	public CustomFieldsExampleServiceImpl() {
		super(
				CustomFieldsExampleDTO.class,
				CustomFieldsExample.class,
				null,
				CustomFieldsExampleFieldMetaBuilder.class
		);
	}

	@Override
	protected CreateResult<CustomFieldsExampleDTO> doCreateEntity(CustomFieldsExample customFieldsExample, BusinessComponent businessComponent) {
		return null;
	}

	@Override
	protected ActionResultDTO<CustomFieldsExampleDTO> doUpdateEntity(CustomFieldsExample customFieldsExample, CustomFieldsExampleDTO customFieldsExampleDTO, BusinessComponent businessComponent) {
		return null;
	}
}
