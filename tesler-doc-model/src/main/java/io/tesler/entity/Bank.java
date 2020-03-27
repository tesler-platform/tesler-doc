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

package io.tesler.entity;

import io.tesler.api.data.dictionary.LOV;
import io.tesler.model.core.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "BANK")
@Getter
@Setter
@NoArgsConstructor
public class Bank extends BaseEntity {

	@Column
	private String name;

	@Column
	private Integer activeProjectsAmount;

	@Column
	private boolean isNational;

	@Column
	private LOV size;

	@Column
	private String notes;

	@Column
	private LOV country;

	@Column
	private String testInput;

	@Column
	private Long testPercent;

	@Column
	private Double testMoney;

	@Column
	private LocalDateTime testDate;

	@Column
	private String testPickList;

	@Column
	private LOV testDictionary;
}
