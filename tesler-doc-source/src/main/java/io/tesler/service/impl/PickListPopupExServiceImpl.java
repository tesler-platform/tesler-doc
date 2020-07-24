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
import io.tesler.dto.PickListPopupExDTO;
import io.tesler.entity.PickListPopupEx;
import io.tesler.service.PickListPopupExService;
import io.tesler.service.meta.PickListPopupExFieldMetaBuilder;
import org.springframework.stereotype.Service;

@Service
public class PickListPopupExServiceImpl extends
	VersionAwareResponseService<PickListPopupExDTO, PickListPopupEx> implements
	PickListPopupExService {

	public PickListPopupExServiceImpl() {
		super(PickListPopupExDTO.class, PickListPopupEx.class, null, PickListPopupExFieldMetaBuilder.class);
	}

	@Override
	protected CreateResult<PickListPopupExDTO> doCreateEntity(PickListPopupEx entity, BusinessComponent bc) {
		return null;
	}

	@Override
	protected ActionResultDTO<PickListPopupExDTO> doUpdateEntity(PickListPopupEx entity, PickListPopupExDTO data,
		BusinessComponent bc) {
		return null;
	}

}
