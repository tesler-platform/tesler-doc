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

import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.service.action.Actions;
import io.tesler.dto.EmployeeDTO;
import io.tesler.dto.EmployeeDTO_;
import io.tesler.entity.Employee;
import io.tesler.entity.LinkBankEmployee;
import io.tesler.entity.LinkBankEmployee_;
import io.tesler.service.EmployeeService;
import io.tesler.service.meta.EmployeeFieldMetaBuilder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends VersionAwareResponseService<EmployeeDTO, Employee> implements EmployeeService {

	public EmployeeServiceImpl() {
		super(EmployeeDTO.class, Employee.class, null, EmployeeFieldMetaBuilder.class);
	}

	/**
	 * <p>Method contains logic of creating Employee entity instance</p>
	 *
	 * @param employee Entity instance.
	 * @param businessComponent Business Component.
	 * @return Returns wrapper object which contains of a list of post actions and DTO related to created entity.
	 */
	@Override
	protected CreateResult<EmployeeDTO> doCreateEntity(Employee employee, BusinessComponent businessComponent) {
		baseDAO.save(employee);
		return new CreateResult<>(entityToDto(businessComponent, employee));
	}

	/**
	 * <p>Method contains logic of updating Employee entity instance</p>
	 *
	 * @param employee Entity instance.
	 * @param employeeDTO Data transfer object related to the entity.
	 * @param businessComponent Business component.
	 * @return Returns wrapper object which contains of a list of post actions and DTO related to updated entity.
	 * Returns null if the fields of the DTO hasn't been changed.
	 */
	@Override
	protected ActionResultDTO<EmployeeDTO> doUpdateEntity(Employee employee, EmployeeDTO employeeDTO,
			BusinessComponent businessComponent) {
		if (!employeeDTO.hasChangedFields()) {
			return null;
		}
		if (employeeDTO.isFieldChanged(EmployeeDTO_.name)) {
			employee.setName(employeeDTO.getName());
		}
		if (employeeDTO.isFieldChanged(EmployeeDTO_.successRate)) {
			employee.setSuccessRate(Integer.parseInt(employeeDTO.getSuccessRate()));
		}
		if (employeeDTO.isFieldChanged(EmployeeDTO_.position)) {
			employee.setPosition(employeeDTO.getPosition());
		}

		return new ActionResultDTO<>(entityToDto(businessComponent, employee));
	}

	/**
	 * <p>Method contains logic of deleting Employee entity instance</p>
	 *
	 * @param bc Business component.
	 * @return Returns wrapper object which contains of a list of post actions and DTO related to deleted entity.
	 */
	@Override
	public ActionResultDTO<EmployeeDTO> deleteEntity(BusinessComponent bc) {
		Employee entity = baseDAO.findById(Employee.class, bc.getIdAsLong());
		baseDAO.getList(
				LinkBankEmployee.class,
				(root, query, cb) ->
						cb.equal(root.get(LinkBankEmployee_.employee), entity)
		).forEach(item -> baseDAO.delete(item));
		baseDAO.delete(entity);
		return new ActionResultDTO<>();
	}

	@Override
	public Actions<EmployeeDTO> getActions() {
		return Actions.<EmployeeDTO>builder()
				.save().add()
				.create().add()
				.delete().add()
				.build();
	}

	@Override
	protected EmployeeDTO entityToDto(BusinessComponent bc, Employee entity) {
		return super.entityToDto(bc, entity);
	}

}
