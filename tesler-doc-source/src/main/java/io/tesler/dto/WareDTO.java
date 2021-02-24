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
import io.tesler.core.dto.multivalue.MultivalueFieldSingleValue;
import io.tesler.core.util.filter.SearchParameter;
import io.tesler.entity.Ware;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WareDTO extends DataResponseDTO {

	@SearchParameter
	private String name;

	private MultivalueField components;

	@SearchParameter
	private String component1;

	@SearchParameter
	private String component2;

	public WareDTO(Ware ware) {
		this.id = ware.getId().toString();
		this.name = ware.getName();

		List<MultivalueFieldSingleValue> comps = new ArrayList<>();

		if (ware.getComponent1() != null) {
			this.component1 = ware.getComponent1();
			comps.add(new MultivalueFieldSingleValue("1", ware.getComponent1()));
		}
		if (ware.getComponent2() != null) {
			this.component2 = ware.getComponent2();
			comps.add(new MultivalueFieldSingleValue("2", ware.getComponent2()));
		}
		this.components = new MultivalueField(comps);
	}
}
