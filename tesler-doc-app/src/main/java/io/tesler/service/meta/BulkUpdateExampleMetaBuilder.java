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

package io.tesler.service.meta;

import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dao.BaseDAO;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import io.tesler.dto.BulkUpdateExampleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.tesler.dto.BulkUpdateExampleDTO_.name;
import static io.tesler.dto.BulkUpdateExampleDTO_.description;

@Service
@RequiredArgsConstructor
public class BulkUpdateExampleMetaBuilder extends FieldMetaBuilder<BulkUpdateExampleDTO> {

	private final BaseDAO baseDAO;

	@Override
	public void buildRowDependentMeta(RowDependentFieldsMeta<BulkUpdateExampleDTO> fields, InnerBcDescription bcDescription, Long rowId, Long parentId) {
		fields.setEnabled(name);
		fields.setEnabled(description);

	}

	@Override
	public void buildIndependentMeta(FieldsMeta<BulkUpdateExampleDTO> fields, InnerBcDescription bcDescription, Long parentId) {
		fields.setEnabled(name);
		fields.setEnabled(description);
	}

}
