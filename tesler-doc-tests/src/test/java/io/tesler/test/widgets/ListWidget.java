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
import static com.codeborne.selenide.Selenide.actions;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.util.List;
import org.openqa.selenium.By;

public class ListWidget {

	private final SelenideElement widget;

	public ListWidget() {
		widget = $(By.cssSelector("div[class^='TableWidget__tableContainer']"));
	}

	public SelenideElement FindRow(String columnName, String columnValue) {
		$(By.cssSelector("div[class^='TableWidget__tableContainer']")).shouldBe(Condition.visible);
		$(By.cssSelector(".ant-table-tbody")).$$(By.cssSelector("tr")).shouldBe(CollectionCondition.sizeGreaterThan(0));
		return $(By.cssSelector(".ant-table-tbody")).$$(By.tagName("tr"))
				.stream()
				.filter(r -> FindColumnInRow(columnName, r).getText().equals(columnValue))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("no row with columnValue found"));
	}

	public ElementsCollection ListRows() {
		return $(By.cssSelector(".ant-table-tbody")).$$(By.tagName("tr"));
	}

	public SelenideElement FindColumnInRow(String columnName, SelenideElement row) {
		$(By.cssSelector("div[class^='TableWidget__tableContainer']")).shouldBe(Condition.visible);
		return row.$$(By.tagName("td")).get(getIndexColumn(columnName));
	}

	public ElementsCollection ListColumnsInRow(SelenideElement row) {
		return row.$$(By.tagName("td"));
	}

	public Integer getIndexColumn(String columnName) {
		$(By.cssSelector("div[class^='TableWidget__tableContainer']")).shouldBe(Condition.visible);
		List<String> tableColumns = $(By.cssSelector("div[class^='TableWidget__tableContainer'] thead"))
				.$$(By.tagName("th")).texts();
		return tableColumns.indexOf(columnName);
	}

	public void ClickActionThreeDots(SelenideElement row, String buttonName) {
		actions().moveToElement(row).perform();
		actions().moveToElement($(By.cssSelector("div[class^=TableWidget__dots]"))).perform();
		$(By.cssSelector("div[class^=TableWidget__dots]")).click();
		$(By.cssSelector("div[class^=ant-dropdown] ul")).$$(By.tagName("li")).find(Condition.text(buttonName)).click();
	}

	public ElementsCollection getComboBoxList(SelenideElement column) {
		column.doubleClick();
		column.$(By.cssSelector(".ant-select-selection--single")).click();
		return $(By.cssSelector("div[class^=ant-select-dropdown] ul")).shouldBe(Condition.visible).$$(By.tagName("li"));
	}

	public void clearComboBoxList(SelenideElement column) {
		column.doubleClick();
		actions().moveToElement(column
				.$(By.className("ant-select-selection__clear"))).perform();
		column
				.$(By.className("ant-select-selection__clear")).shouldBe(Condition.visible).click();
	}


}
