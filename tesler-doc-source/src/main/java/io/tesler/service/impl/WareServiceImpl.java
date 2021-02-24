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

package io.tesler.service.impl;

import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dto.DrillDownType;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.dto.rowmeta.PostAction;
import io.tesler.core.service.action.Actions;
import io.tesler.dto.WareDTO;
import io.tesler.dto.WareDTO_;
import io.tesler.entity.Ware;
import io.tesler.model.core.dao.JpaDao;
import io.tesler.service.WareService;
import io.tesler.service.meta.WareFieldMetaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WareServiceImpl extends VersionAwareResponseService<WareDTO, Ware> implements WareService {

	@Autowired
	private JpaDao jpaDao;

	public WareServiceImpl() {
		super(WareDTO.class, Ware.class, null, WareFieldMetaBuilder.class);
	}

	@Override
	protected CreateResult<WareDTO> doCreateEntity(Ware ware, BusinessComponent businessComponent) {
		jpaDao.save(ware);
		return new CreateResult<>(entityToDto(businessComponent, ware))
				.setAction(PostAction.drillDown(
						DrillDownType.INNER,
						"/screen/example/view/warecreation/" + businessComponent.getName() + "/" + ware.getId().toString()
				));
	}

	@Override
	protected ActionResultDTO<WareDTO> doUpdateEntity(Ware ware, WareDTO wareDTO, BusinessComponent businessComponent) {
		if (wareDTO.hasChangedFields()) {
			if (wareDTO.isFieldChanged(WareDTO_.name)) {
				ware.setName(wareDTO.getName());
			}
			if (wareDTO.isFieldChanged(WareDTO_.component1)) {
				ware.setComponent1(wareDTO.getComponent1());
			}
			if (wareDTO.isFieldChanged(WareDTO_.component2)) {
				ware.setComponent2(wareDTO.getComponent2());
			}
		}

		return new ActionResultDTO<>(entityToDto(businessComponent, ware));
	}

	@Override
	public ActionResultDTO<WareDTO> deleteEntity(BusinessComponent bc) {
		Ware ware = jpaDao.findById(Ware.class, bc.getIdAsLong());
		baseDAO.delete(ware);

		return new ActionResultDTO<>();
	}

	@Override
	public Actions<WareDTO> getActions() {
		return Actions.<WareDTO>builder()
				.create().add()
				.save().add()
				.delete().add()
				.build();
	}

	@Override
	protected WareDTO entityToDto(BusinessComponent bc, Ware entity) {
		return super.entityToDto(bc, entity);
	}
}
