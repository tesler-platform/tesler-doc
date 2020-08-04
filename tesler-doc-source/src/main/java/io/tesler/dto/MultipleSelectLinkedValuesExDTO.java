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

package io.tesler.dto;

import io.tesler.api.data.dto.DataResponseDTO;
import io.tesler.core.dto.multivalue.MultivalueField;
import io.tesler.entity.MultipleSelectLinkedValuesEx;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
public class MultipleSelectLinkedValuesExDTO extends DataResponseDTO {

	private MultivalueField countryList;

	private MultivalueField languageList;

	public MultipleSelectLinkedValuesExDTO(MultipleSelectLinkedValuesEx exEntity) {
		this.id = exEntity.getId().toString();
		this.languageList = new MultivalueField(Collections.emptyList());
		this.countryList = new MultivalueField(Collections.emptyList());
	}
}
