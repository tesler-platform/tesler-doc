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
import io.tesler.dto.AssocListPopupExDTO;
import io.tesler.entity.AssocListPopupEx;
import io.tesler.service.AssocListPopupExService;
import io.tesler.service.meta.AssocListPopupExFieldMetaBuilder;
import org.springframework.stereotype.Service;

@Service
public class AssocListPopupExServiceImpl extends VersionAwareResponseService<AssocListPopupExDTO, AssocListPopupEx> implements
	AssocListPopupExService {

	public AssocListPopupExServiceImpl() {
		super(AssocListPopupExDTO.class, AssocListPopupEx.class, null, AssocListPopupExFieldMetaBuilder.class);
	}

	@Override
	protected CreateResult<AssocListPopupExDTO> doCreateEntity(AssocListPopupEx entity, BusinessComponent bc) {
		return null;
	}

	@Override
	protected ActionResultDTO<AssocListPopupExDTO> doUpdateEntity(AssocListPopupEx entity, AssocListPopupExDTO data,
		BusinessComponent bc) {
		return null;
	}

}
