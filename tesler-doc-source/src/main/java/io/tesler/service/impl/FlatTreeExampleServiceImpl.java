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
import io.tesler.core.dto.rowmeta.PostAction;
import io.tesler.core.service.action.Actions;
import io.tesler.dto.FlatTreeExampleDTO;
import io.tesler.entity.FlatTreeExample;
import io.tesler.service.FlatTreeExampleService;
import io.tesler.service.meta.FlatTreeExampleMetaBuilder;
import org.springframework.stereotype.Service;

import static io.tesler.dto.FlatTreeExampleDTO_.name;

@Service
public class FlatTreeExampleServiceImpl
		extends VersionAwareResponseService<FlatTreeExampleDTO, FlatTreeExample>
		implements FlatTreeExampleService {

	public FlatTreeExampleServiceImpl() {
		super(FlatTreeExampleDTO.class, FlatTreeExample.class, null, FlatTreeExampleMetaBuilder.class);
	}

	@Override
	protected CreateResult<FlatTreeExampleDTO> doCreateEntity(FlatTreeExample entity, BusinessComponent bc) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected ActionResultDTO<FlatTreeExampleDTO> doUpdateEntity(FlatTreeExample entity, FlatTreeExampleDTO data, BusinessComponent bc) {
		if (data.hasChangedFields()) {
			if (data.isFieldChanged(name)) {
				entity.setName(data.getName());
			}
		}
		return new ActionResultDTO<>(entityToDto(bc, entity))
				.setAction(PostAction.downloadFile("9"));
	}

	@Override
	public Actions<FlatTreeExampleDTO> getActions() {
		return Actions.<FlatTreeExampleDTO>builder()
				.save().add()
				.build();
	}
}
