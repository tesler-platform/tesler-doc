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
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.service.action.Actions;
import io.tesler.dto.ForPickExDTO;
import io.tesler.entity.ForPickEx;
import io.tesler.service.ForPickExService;
import io.tesler.service.meta.ForPickExFieldMetaBuilder;
import org.springframework.stereotype.Service;

@Service
public class ForPickExServiceImpl extends VersionAwareResponseService<ForPickExDTO, ForPickEx> implements
	ForPickExService {

	public ForPickExServiceImpl() {
		super(ForPickExDTO.class, ForPickEx.class, null, ForPickExFieldMetaBuilder.class);
	}

	@Override
	protected CreateResult<ForPickExDTO> doCreateEntity(ForPickEx entity, BusinessComponent bc) {
		baseDAO.save(entity);
		return new CreateResult<ForPickExDTO>(entityToDto(bc, entity));
	}

	@Override
	protected ActionResultDTO<ForPickExDTO> doUpdateEntity(ForPickEx entity, ForPickExDTO data, BusinessComponent bc) {
		return null;
	}

	@Override
	public Actions<ForPickExDTO> getActions() {
		return Actions.<ForPickExDTO>builder().create().add().build();
	}

}
