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

public class ListWidget {

	private final SelenideElement widget;

	private ListHelper helper;

	public ListWidget() {
		widget = $(By.cssSelector("div[class^='TableWidget__tableContainer']"));
		this.helper = new ListHelper(widget);
	}

	public SelenideElement FindRow(String columnName, String columnValue) {
		return helper.FindRow(columnName, columnValue);
	}

	public SelenideElement FindRow(Long rowId) {
		return helper.FindRow(rowId);
	}

	public Long FindRowId(String columnName, String columnValue) {
		return helper.FindRowId(columnName, columnValue);
	}

	public ElementsCollection ListRows() {
		return helper.ListRows();
	}

	public SelenideElement FindColumnInRow(String columnName, SelenideElement row) {
		return helper.FindColumnInRow(columnName, row);
	}

	public ElementsCollection ListColumnsInRow(SelenideElement row) {
		return helper.ListColumnsInRow(row);
	}

	public Integer getIndexColumn(String columnName) {
		return helper.getIndexColumn(columnName);
	}

	public ElementsCollection getComboBoxList(SelenideElement column) {
		column.doubleClick();
		column.$(By.cssSelector(".ant-select-selection--single")).click();
		return $(By.cssSelector("div[class^=ant-select-dropdown] ul")).shouldBe(Condition.visible).$$(By.tagName("li"));
	}

	public void getPickList(SelenideElement column) {
		column.$(By.cssSelector("i[class='anticon anticon-paper-clip']")).click();
	}

	public void setDateCurrentDay(SelenideElement column) {
		column.doubleClick();
		column.click();
		$(By.cssSelector(".ant-calendar-table")).shouldBe(Condition.visible);
		$(By.cssSelector(".ant-calendar-footer a")).click();
	}

	public void ClickActionThreeDots(SelenideElement row, String buttonName) {
		actions().moveToElement(row).perform();
		actions().moveToElement($(By.cssSelector("div[class^=TableWidget__dots]"))).perform();
		$(By.cssSelector("div[class^=TableWidget__dots]")).click();
		$(By.cssSelector("div[class^=ant-dropdown] ul")).$$(By.tagName("li")).find(Condition.text(buttonName)).click();
	}

	public void ClickButtonList(String buttonName) {
		$$(By.cssSelector("div[class^='Card__container']"))
				.stream()
				.filter(r -> r.$(By.cssSelector("div[class^='TableWidget__tableContainer']")).is(Condition.exist))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("no elements found"))
				.$(By.cssSelector("div[class^='Operations__container']")).$$(By.cssSelector("button"))
				.findBy(Condition.text(buttonName)).click();
	}

	public void clearComboBoxList(SelenideElement column) {
		column.doubleClick();
		actions().moveToElement(column
				.$(By.className("ant-select-selection__clear"))).perform();
		column
				.$(By.className("ant-select-selection__clear")).shouldBe(Condition.visible).click();
	}

	public void clearDatePicker(SelenideElement column) {
		column.doubleClick();
		actions().moveToElement(column
				.$(By.className("ant-calendar-picker-clear"))).perform();
		column
				.$(By.className("ant-calendar-picker-clear")).shouldBe(Condition.visible).click();
	}

	public void setFilterInput(String columnName, String value) {
		helper.ListColumns().findBy(Condition.exactText(columnName)).$(By.cssSelector("div[class^='ColumnFilter']"))
				.click();
		$(By.cssSelector(".ant-popover-inner-content input")).setValue(value);
		$(By.cssSelector(".ant-popover-inner-content")).$$(By.tagName("button")).findBy(Condition.text("Apply")).click();
	}

	public void clearFilterInput(String columnName) {
		helper.ListColumns().findBy(Condition.exactText(columnName)).$(By.cssSelector("div[class^='ColumnFilter']"))
				.click();
		$(By.cssSelector(".ant-popover-inner-content")).$$(By.tagName("button")).findBy(Condition.text("Clear")).click();
	}

	public void clearAllFilters() {
		$(By.cssSelector("div[class^='TableWidget__filtersContainer'] a")).click();
	}

}
