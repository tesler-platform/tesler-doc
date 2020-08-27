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
import io.tesler.dto.BulkInsertExampleDTO;
import io.tesler.entity.BulkInsertExample;
import io.tesler.model.core.dao.JpaDao;
import io.tesler.model.core.entity.FileEntity;
import io.tesler.service.BulkInsertExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static io.tesler.dto.BulkInsertExampleDTO_.fileId;
import static io.tesler.dto.BulkInsertExampleDTO_.name;

@Service
public class BulkInsertExampleServiceImpl
		extends VersionAwareResponseService<BulkInsertExampleDTO, BulkInsertExample>
		implements BulkInsertExampleService {

	@Autowired
	protected JpaDao jpaDao;

	public BulkInsertExampleServiceImpl() {
		super(BulkInsertExampleDTO.class, BulkInsertExample.class, null, null);
	}

	@Override
	protected CreateResult<BulkInsertExampleDTO> doCreateEntity(BulkInsertExample bulkInsertExample, BusinessComponent businessComponent) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected ActionResultDTO<BulkInsertExampleDTO> doUpdateEntity(BulkInsertExample entity, BulkInsertExampleDTO data, BusinessComponent bc) {
		if (data.hasChangedFields()) {
			if (data.isFieldChanged(fileId)) {
				FileEntity file = jpaDao.findById(FileEntity.class, Long.parseLong(data.getFileId()));
				entity.setFileEntity(file);
			}
			if (data.isFieldChanged(name)) {
				entity.setName(data.getName());
			}
		}
		return new ActionResultDTO<>(entityToDto(bc, entity));
	}


	@Override
	public Actions<BulkInsertExampleDTO> getActions() {
		return Actions.<BulkInsertExampleDTO>builder()
				.action("file-upload", "File Upload")
					.scope(ActionScope.BC).add()
				.action("file-upload-save", "File Upload Save")
					.scope(ActionScope.BC).invoker(this::fileUpload).add()
				.delete().add()
				.build();
	}

	private ActionResultDTO<BulkInsertExampleDTO> fileUpload(BusinessComponent bc, BulkInsertExampleDTO data) {
		Optional.ofNullable(data.getBulkIds()).ifPresent(res -> {
			res.forEach(item -> {
				BulkInsertExample itemResult = new BulkInsertExample();
				Long id = Long.parseLong(item);
				FileEntity file = jpaDao.findById(FileEntity.class, id);
				itemResult.setFileEntity(file);
				itemResult.setName("Result " + UUID.randomUUID().toString().substring(0, 7));
				baseDAO.save(itemResult);
			});
		});
		return new ActionResultDTO<BulkInsertExampleDTO>();
	}
}
