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

package io.tesler.core.dict;

import static io.tesler.api.data.dictionary.DictionaryCache.dictionary;

import io.tesler.api.data.dictionary.IDictionaryType;
import io.tesler.api.data.dictionary.LOV;
import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TDDictionaryType implements Serializable, IDictionaryType {

	COUNTRY,
	INTERNAL_ROLE,
	SIZE,
	SYSTEM_PREF,
	TYPE,
	DOC_TEST,
	LANGUAGE;


	@Override
	public LOV lookupName(String val) {
		return dictionary().lookupName(val, this);
	}

	@Override
	public String lookupValue(io.tesler.api.data.dictionary.LOV lov) {
		return dictionary().lookupValue(lov, this);
	}

	@Override
	public String getName() {
		return name();
	}
}

