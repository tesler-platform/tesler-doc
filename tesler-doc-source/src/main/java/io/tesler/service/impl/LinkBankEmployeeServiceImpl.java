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

import io.tesler.api.data.dto.AssociateDTO;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dao.BaseDAO;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.AssociateResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.service.action.Actions;
import io.tesler.dto.LinkBankEmployeeDTO;
import io.tesler.entity.Bank;
import io.tesler.entity.Employee;
import io.tesler.entity.LinkBankEmployee;
import io.tesler.entity.LinkBankEmployee_;
import io.tesler.service.LinkBankEmployeeService;
import io.tesler.service.meta.LinkBankEmployeeFieldMetaBuilder;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkBankEmployeeServiceImpl extends
		VersionAwareResponseService<LinkBankEmployeeDTO, LinkBankEmployee> implements
		LinkBankEmployeeService {

	@Autowired
	private BaseDAO baseDAO;

	public LinkBankEmployeeServiceImpl() {
		super(LinkBankEmployeeDTO.class, LinkBankEmployee.class,
				LinkBankEmployee_.bank, LinkBankEmployeeFieldMetaBuilder.class);
	}

	/***
	 * <p>Method contains logic of create action for LinkBankEmployee entity instance</p>
	 * @param linkBankEmployee Entity instance.
	 * @param businessComponent Business component.
	 * @return Method throws an exception with UnsupportedOperationException type(in this case returns nothing),
	because operation of that type cant be applied to the entity.
	 */
	@Override
	protected CreateResult<LinkBankEmployeeDTO> doCreateEntity(LinkBankEmployee linkBankEmployee,
			BusinessComponent businessComponent) {
		throw new UnsupportedOperationException();
	}

	/***
	 *<p>Method contains logic of updating LinkBankEmployee entity instance</p>
	 * @param linkBankEmployee Entity instance.
	 * @param linkBankEmployeeDTO Data transfer object related to the entity
	 * @param businessComponent Business component.
	 * @return Method throws an exception with UnsupportedOperationException type(in this case returns nothing),
	 * 	because operation of that type cant be applied to the entity.
	 */
	@Override
	protected ActionResultDTO<LinkBankEmployeeDTO> doUpdateEntity(LinkBankEmployee linkBankEmployee,
			LinkBankEmployeeDTO linkBankEmployeeDTO, BusinessComponent businessComponent) {
		throw new UnsupportedOperationException();
	}

	/**
	 * <p></p>
	 *
	 * @param data A list of associated DTO's (user selects them)
	 * @param bc Business component
	 * @return Returns wrapper object which contains of a list of post actions and DTO's which have been associated.
	 */
	@Override
	protected AssociateResultDTO doAssociate(List<AssociateDTO> data, BusinessComponent bc) {
		for (AssociateDTO dto : data) {
			if (dto.getAssociated()) {
				LinkBankEmployee linkBankEmployee = new LinkBankEmployee();
				linkBankEmployee.setEmployee(baseDAO.findById(Employee.class, Long.valueOf(dto.getId())));
				linkBankEmployee.setBank(baseDAO.findById(Bank.class, bc.getParentIdAsLong()));
				baseDAO.save(linkBankEmployee);
			}
		}
		return new AssociateResultDTO(Collections.emptyList());
	}

	@Override
	protected LinkBankEmployeeDTO entityToDto(BusinessComponent bc, LinkBankEmployee entity) {
		return super.entityToDto(bc, entity);
	}

	@Override
	public Actions<LinkBankEmployeeDTO> getActions() {
		return Actions.<LinkBankEmployeeDTO>builder()
				.associate().add()
				.delete().add()
				.build();
	}

}
