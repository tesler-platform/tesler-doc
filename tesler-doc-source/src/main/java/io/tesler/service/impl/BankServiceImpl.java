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
import io.tesler.core.dict.TDDictionaryType;
import io.tesler.core.dto.DrillDownType;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.dto.rowmeta.PostAction;
import io.tesler.core.service.action.Actions;
import io.tesler.crudma.config.TESLERDOCServiceAssociation;
import io.tesler.dto.BankDTO;
import io.tesler.dto.BankDTO_;
import io.tesler.entity.Bank;
import io.tesler.entity.LinkBankEmployee;
import io.tesler.entity.LinkBankEmployee_;
import io.tesler.model.core.dao.JpaDao;
import io.tesler.service.BankService;
import io.tesler.service.meta.BankFieldMetaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl extends VersionAwareResponseService<BankDTO, Bank> implements BankService {

	@Autowired
	private JpaDao jpaDao;

	public BankServiceImpl() {
		super(BankDTO.class, Bank.class, null, BankFieldMetaBuilder.class);
	}

	@Override
	protected BankDTO entityToDto(BusinessComponent bc, Bank entity) {
		return super.entityToDto(bc, entity);
	}

	@Override
	protected CreateResult<BankDTO> doCreateEntity(Bank bank, BusinessComponent businessComponent) {
		jpaDao.save(bank);
		if (businessComponent.getName().toLowerCase().equals("bankdoc")) {
			return new CreateResult<>(entityToDto(businessComponent, bank))
					.setAction(PostAction.drillDown(
							DrillDownType.INNER,
							"/screen/components/view/form/" + businessComponent.getName() + "/" + bank.getId().toString()
					));
		}
		return new CreateResult<>(entityToDto(businessComponent, bank))
				.setAction(PostAction.drillDown(
						DrillDownType.INNER,
						"/screen/example/view/bankcard/" + TESLERDOCServiceAssociation.bank + "/" + bank.getId().toString()
				));
	}

	@Override
	protected ActionResultDTO<BankDTO> doUpdateEntity(Bank bank, BankDTO bankDTO, BusinessComponent businessComponent) {
		if (bankDTO.hasChangedFields()) {
			if (bankDTO.isFieldChanged(BankDTO_.name)) {
				bank.setName(bankDTO.getName());
			}
			if (bankDTO.isFieldChanged(BankDTO_.activeProjectsAmount)) {
				bank.setActiveProjectsAmount(bankDTO.getActiveProjectsAmount());
			}
			if (bankDTO.isFieldChanged(BankDTO_.country)) {
				bank.setCountry(TDDictionaryType.COUNTRY.lookupName(bankDTO.getCountry()));
			}
			if (bankDTO.isFieldChanged(BankDTO_.size)) {
				bank.setSize(TDDictionaryType.SIZE.lookupName(bankDTO.getSize()));
			}
			if (bankDTO.isFieldChanged(BankDTO_.notes)) {
				bank.setNotes(bankDTO.getNotes());
			}
			if (bankDTO.isFieldChanged(BankDTO_.isNational)) {
				bank.setNational(bankDTO.getIsNational());
			}
			if (bankDTO.isFieldChanged(BankDTO_.testInput)) {
				bank.setTestInput(bankDTO.getTestInput());
			}
			if (bankDTO.isFieldChanged(BankDTO_.testPercent)) {
				bank.setTestPercent(bankDTO.getTestPercent());
			}
			if (bankDTO.isFieldChanged(BankDTO_.testMoney)) {
				bank.setTestMoney(bankDTO.getTestMoney());
			}
			if (bankDTO.isFieldChanged(BankDTO_.testDate)) {
				bank.setTestDate(bankDTO.getTestDate());
			}
			if (bankDTO.isFieldChanged(BankDTO_.testDictionary)) {
				bank.setTestDictionary(TDDictionaryType.DOC_TEST.lookupName(bankDTO.getTestDictionary()));
			}
			if (bankDTO.isFieldChanged(BankDTO_.testRadio)) {
				bank.setTestRadio(TDDictionaryType.DOC_TEST.lookupName(bankDTO.getTestRadio()));
			}
			if (bankDTO.isFieldChanged(BankDTO_.testPickList)) {
				bank.setTestPickList(bankDTO.getTestPickList());
			}
		}

		return new ActionResultDTO<>(entityToDto(businessComponent, bank));

	}

	@Override
	public ActionResultDTO<BankDTO> deleteEntity(BusinessComponent bc) {
		Bank entity = jpaDao.findById(Bank.class, bc.getIdAsLong());
		baseDAO.getList(
				LinkBankEmployee.class,
				(root, query, cb) ->
						cb.equal(root.get(LinkBankEmployee_.bank), entity)
		).forEach(item -> baseDAO.delete(item));
		baseDAO.delete(entity);
		return new ActionResultDTO<>();
	}

	@Override
	public Actions<BankDTO> getActions() {
		return Actions.<BankDTO>builder()
				.create().add()
				.save().add()
				.delete().add()
				.build();
	}


}
