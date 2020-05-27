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

import io.tesler.api.data.dictionary.LOV;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Dictionaries {

	@UtilityClass
	public static class Size {

		public static final LOV SMALL = new LOV("SMALL");

		public static final LOV MEDIUM = new LOV("MEDIUM");

		public static final LOV LARGE = new LOV("LARGE");

	}

	@UtilityClass
	public static class Country {

		public static final LOV GERMANY = new LOV("GERMANY");

		public static final LOV UK = new LOV("UK");

		public static final LOV RUSSIA = new LOV("RUSSIA");

	}

	@UtilityClass
	public static class Language {

		public static final LOV GERMAN = new LOV("GERMAN");

		public static final LOV DANISH = new LOV("DANISH");

		public static final LOV LOW_GERMAN = new LOV("LOW_GERMAN");

		public static final LOV SORBIAN = new LOV("SORBIAN");

		public static final LOV ROMANY = new LOV("ROMANY");

		public static final LOV FRISIAN = new LOV("FRISIAN");

		public static final LOV ENGLISH = new LOV("ENGLISH");

		public static final LOV SCOTS = new LOV("SCOTS");

		public static final LOV WELSH = new LOV("WELSH");

		public static final LOV RUSSIAN = new LOV("RUSSIAN");

		public static final LOV UKRAINIAN = new LOV("UKRAINIAN");

		public static final LOV BURYAT = new LOV("BURYAT");

		public static final LOV UDMURT = new LOV("UDMURT");

	}

}
