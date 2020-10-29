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

package io.tesler.crudma.config;

import io.tesler.core.crudma.bc.BcIdentifier;
import io.tesler.core.crudma.bc.EnumBcIdentifier;
import io.tesler.core.crudma.bc.impl.AbstractEnumBcSupplier;
import io.tesler.core.crudma.bc.impl.BcDescription;
import io.tesler.service.*;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public enum TESLERDOCServiceAssociation implements EnumBcIdentifier {

	// @formatter:off


	bank(BankService.class),
		linkBankEmployee(bank,LinkBankEmployeeService.class),
			linkBankEmployeeAssoc(bank, EmployeeService.class),

	bankDoc(BankService.class),
		bankDocPicklist(bankDoc, BankService.class),
		linkBankDocEmployee(bankDoc,LinkBankEmployeeService.class),
			linkBankDocEmployeeAssoc(bankDoc,EmployeeService.class),

	employee(EmployeeService.class),

	bulkInsertExample(BulkInsertExampleService.class),
	bulkUpdateExample(BulkUpdateExampleService.class),

	// assocListPopup example
	forAssocEx(ForAssocExService.class),
	assocListPopupExAssoc(AssocListPopupExService.class),
	forAssocCustomEx(ForAssocExService.class),
	assocListPopupCustomExAssoc(AssocListPopupExService.class),

	// assocListPopup example
	forPickEx(ForPickExService.class),
		pickListPopupEx(PickListPopupExService.class), // fixme must has 'forPickEx' as parent
	forPickCustomEx(ForPickExService.class),
		pickListPopupCustomEx(PickListPopupExService.class),

	customFieldsExample(CustomFieldsExampleService.class),

	forceActiveExample(ForceActiveExampleService.class);

	// @formatter:on

	public static final EnumBcIdentifier.Holder<TESLERDOCServiceAssociation> Holder = new Holder<>(
			TESLERDOCServiceAssociation.class);

	private final BcDescription bcDescription;

	TESLERDOCServiceAssociation(String parentName, Class<?> serviceClass, boolean refresh) {
		this.bcDescription = buildDescription(parentName, serviceClass, refresh);
	}

	TESLERDOCServiceAssociation(String parentName, Class<?> serviceClass) {
		this(parentName, serviceClass, false);
	}

	TESLERDOCServiceAssociation(BcIdentifier parent, Class<?> serviceClass, boolean refresh) {
		this(parent == null ? null : parent.getName(), serviceClass, refresh);
	}

	TESLERDOCServiceAssociation(BcIdentifier parent, Class<?> serviceClass) {
		this(parent, serviceClass, false);
	}

	TESLERDOCServiceAssociation(Class<?> serviceClass, boolean refresh) {
		this((String) null, serviceClass, refresh);
	}

	TESLERDOCServiceAssociation(Class<?> serviceClass) {
		this((String) null, serviceClass, false);
	}

	@Component
	public static class TESLERDOCBcSupplier extends AbstractEnumBcSupplier<TESLERDOCServiceAssociation> {

		public TESLERDOCBcSupplier() {
			super(TESLERDOCServiceAssociation.Holder);
		}

	}

}
