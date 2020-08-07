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

package io.tesler.utils;

import io.tesler.core.dict.TDDictionaryType;
import io.tesler.core.dto.multivalue.MultivalueField;
import io.tesler.core.dto.multivalue.MultivalueFieldSingleValue;
import io.tesler.entity.mvgstorage.MVGStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MultivalueHelper {
	public <T extends MVGStorage> MultivalueField assignDTO(Set<T> entities, TDDictionaryType dictionaryType) {
		if (entities == null) {
			return new MultivalueField(Collections.emptyList());
		}
		return new MultivalueField(entities.stream().map(entity ->
				new MultivalueFieldSingleValue(entity.getId().toString(),
						dictionaryType.lookupValue(entity.getValue())
				)).collect(Collectors.toList()));
	}
}

