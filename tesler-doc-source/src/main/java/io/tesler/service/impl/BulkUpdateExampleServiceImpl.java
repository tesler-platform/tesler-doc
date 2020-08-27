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
import io.tesler.core.service.action.ActionScope;
import io.tesler.core.service.action.Actions;
import io.tesler.core.service.action.TeslerActionIconSpecifier;
import io.tesler.dto.BulkUpdateExampleDTO;
import io.tesler.entity.BulkUpdateExample;
import io.tesler.service.BulkUpdateExampleService;
import io.tesler.service.meta.BulkUpdateExampleMetaBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static io.tesler.dto.BulkUpdateExampleDTO_.name;
import static io.tesler.dto.BulkUpdateExampleDTO_.description;

@Service
public class BulkUpdateExampleServiceImpl
		extends VersionAwareResponseService<BulkUpdateExampleDTO, BulkUpdateExample>
		implements BulkUpdateExampleService {

	public BulkUpdateExampleServiceImpl() {
		super(
				BulkUpdateExampleDTO.class,
				BulkUpdateExample.class,
				null,
				BulkUpdateExampleMetaBuilder.class
		);
	}

	@Override
	protected CreateResult<BulkUpdateExampleDTO> doCreateEntity(BulkUpdateExample bulkInsertExample, BusinessComponent businessComponent) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected ActionResultDTO<BulkUpdateExampleDTO> doUpdateEntity(BulkUpdateExample entity, BulkUpdateExampleDTO data, BusinessComponent bc) {
		if (data.hasChangedFields()) {
			if (data.isFieldChanged(name)) {
				entity.setName(data.getName());
			}
			if (data.isFieldChanged(description)) {
				entity.setDescription(data.getDescription());
			}
		}
		return new ActionResultDTO<>(entityToDto(bc, entity));
	}


	@Override
	public Actions<BulkUpdateExampleDTO> getActions() {
		return Actions.<BulkUpdateExampleDTO>builder()
				.action("mock-create", "Create")
					.scope(ActionScope.BC)
					.withIcon(TeslerActionIconSpecifier.PLUS, false)
					.invoker(this::mockCreate)
					.add()
				.action("bulk-update", "Update").invoker(this::bulkUpdate)
					.scope(ActionScope.BC)
					.withIcon(TeslerActionIconSpecifier.SAVE, false)
					.add()
				.action("bulk-delete", "Delete").invoker(this::bulkDelete)
					.scope(ActionScope.BC)
					.withIcon(TeslerActionIconSpecifier.DELETE, false)
					.add()
				.build();
	}

	private ActionResultDTO<BulkUpdateExampleDTO> bulkDelete(BusinessComponent bc, BulkUpdateExampleDTO data) {
		Optional.ofNullable(data.getBulkIds()).ifPresent(res -> {
			res.forEach(item -> {
				BulkUpdateExample entity = baseDAO.findById(BulkUpdateExample.class, Long.parseLong(item));
				baseDAO.delete(entity);
			});
		});
		return new ActionResultDTO<BulkUpdateExampleDTO>();
	}

	private ActionResultDTO<BulkUpdateExampleDTO> bulkUpdate(BusinessComponent bc, BulkUpdateExampleDTO data) {
		Optional.ofNullable(data.getBulkIds()).ifPresent(res -> {
			res.forEach(item -> {
				BulkUpdateExample entity = baseDAO.findById(BulkUpdateExample.class, Long.parseLong(item));
				if (data.isFieldChanged(name)) {
					entity.setName(data.getName());
				}
				if (data.isFieldChanged(description)) {
					entity.setDescription(data.getDescription());
				}
				baseDAO.save(entity);
			});
		});
		return new ActionResultDTO<BulkUpdateExampleDTO>();
	}

	private ActionResultDTO<BulkUpdateExampleDTO> mockCreate(BusinessComponent bc, BulkUpdateExampleDTO data) {
		BulkUpdateExample entity = new BulkUpdateExample();
		entity.setName(UUID.randomUUID().toString().substring(0, 7));
		entity.setDescription(UUID.randomUUID().toString().substring(0, 7));
		baseDAO.save(entity);
		return new ActionResultDTO<BulkUpdateExampleDTO>();
	}
}
