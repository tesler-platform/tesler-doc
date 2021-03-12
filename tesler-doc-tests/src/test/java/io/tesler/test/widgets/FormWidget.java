/*-
 * #%L
 * TESLERDOC - Tests
 * %%
 * Copyright (C) 2020 - 2021 Tesler Contributors
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

package io.tesler.test.widgets;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.actions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class FormWidget {

	private SelenideElement widget;

	public FormWidget() {
		widget = $(By.cssSelector(".ant-form"));
	}

	public SelenideElement getInput(String fieldName) {
		$$(By.cssSelector(".ant-form-item")).findBy(Condition.exactText(fieldName)).$(By.tagName("input"))
				.shouldBe(Condition.exist);
		return $$(By.cssSelector(".ant-form-item")).findBy(Condition.exactText(fieldName)).$(By.tagName("input"));
	}

	public SelenideElement getInputwithErrors(String fieldName, String errorText) {
		return $$(By.cssSelector(".ant-form-item")).findBy(Condition.exactText(fieldName + "\n" + errorText))
				.$(By.tagName("input"));
	}

	public void clickButtonForm(String buttonName) {
		$$(By.cssSelector("div[class^='Card__container']"))
				.stream()
				.filter(r -> r.$(By.cssSelector(".ant-form")).is(Condition.exist))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("no elements found"))
				.$(By.cssSelector("div[class^='Operations__container']")).$$(By.cssSelector("button"))
				.findBy(Condition.text(buttonName)).click();
	}

	public SelenideElement getComboBox(String fieldName) {
		$$(By.cssSelector(".ant-form-item")).findBy(Condition.text(fieldName)).shouldBe(Condition.exist);
		return $$(By.cssSelector(".ant-form-item")).findBy(Condition.text(fieldName));
	}

	public ElementsCollection getComboBoxList(String fieldName) {
		return getComboBox(fieldName).$(By.cssSelector("div[class^=ant-select-dropdown] ul")).shouldBe(Condition.visible)
				.$$(By.tagName("li"));
	}

	public SelenideElement callPickList(String fieldName) {
		$$(By.cssSelector(".ant-form-item")).findBy(Condition.exactText(fieldName))
				.$(By.cssSelector("i[class='anticon anticon-paper-clip']")).shouldBe(Condition.exist);
		return $$(By.cssSelector(".ant-form-item")).findBy(Condition.exactText(fieldName))
				.$(By.cssSelector("i[class='anticon anticon-paper-clip']"));
	}

	public SelenideElement getPickList(String fieldName) {
		$$(By.cssSelector(".ant-form-item")).findBy(Condition.exactText(fieldName)).shouldBe(Condition.exist);
		return $$(By.cssSelector(".ant-form-item")).findBy(Condition.exactText(fieldName));
	}

	public void clearComboBox(String fieldName) {
		actions().moveToElement(getComboBox(fieldName).$(By.className("ant-select-selection__clear"))).perform();
		getComboBox(fieldName).$(By.className("ant-select-selection__clear")).shouldBe(Condition.visible).click();
	}

	public void clearPickList(String fieldName) {
		getPickList(fieldName).$(By.className("ant-input-suffix"))
				.click();
	}

	public void setDateCurrentDay(SelenideElement itemControlDate) {
		itemControlDate.click();
		$(By.cssSelector(".ant-calendar-table")).shouldBe(Condition.visible);
		$(By.cssSelector(".ant-calendar-tbody"))
				.$$(By.tagName("tr")).findBy(Condition.cssClass("ant-calendar-current-week"))
				.$$(By.tagName("td"))
				.findBy(Condition.cssClass("ant-calendar-today"))
				.click();
	}
}
