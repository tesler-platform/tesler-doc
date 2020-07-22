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
import io.tesler.core.service.action.Actions;
import io.tesler.dto.ForAssocExDTO;
import io.tesler.entity.ForAssocEx;
import io.tesler.service.ForAssocExService;
import io.tesler.service.meta.ForAssocExFieldMetaBuilder;
import org.springframework.stereotype.Service;

@Service
public class ForAssocExServiceImpl extends VersionAwareResponseService<ForAssocExDTO, ForAssocEx> implements
	ForAssocExService {

	public ForAssocExServiceImpl() {
		super(ForAssocExDTO.class, ForAssocEx.class, null, ForAssocExFieldMetaBuilder.class);
	}

	@Override
	protected CreateResult<ForAssocExDTO> doCreateEntity(ForAssocEx entity, BusinessComponent bc) {
		baseDAO.save(entity);
		return new CreateResult<ForAssocExDTO>(entityToDto(bc, entity));
	}

	@Override
	protected ActionResultDTO<ForAssocExDTO> doUpdateEntity(ForAssocEx entity, ForAssocExDTO data,
		BusinessComponent bc) {
		return null;
	}

	@Override
	public Actions<ForAssocExDTO> getActions() {
		return Actions.<ForAssocExDTO>builder().create().add().build();
	}

}
