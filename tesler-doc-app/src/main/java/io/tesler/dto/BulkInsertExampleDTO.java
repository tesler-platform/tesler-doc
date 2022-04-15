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

package io.tesler.dto;

import io.tesler.api.data.dto.DataResponseDTO;
import io.tesler.entity.BulkInsertExample;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class BulkInsertExampleDTO extends DataResponseDTO {

	private String name;

	private String fileName;

	private String fileId;

	private List<String> bulkIds;

	public BulkInsertExampleDTO(BulkInsertExample entity) {
		this.id = entity.getId().toString();
		this.name = entity.getName();
		this.fileName = Optional.ofNullable(entity.getFileEntity()).map(res -> res.getFileName()).orElse(null);
		this.fileId = Optional.ofNullable(entity.getFileEntity()).map(res -> res.getId().toString()).orElse(null);
	}

}
