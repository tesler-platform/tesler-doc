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

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.util.List;
import org.openqa.selenium.By;

public class ListHelper {

	private SelenideElement widget;

	public ListHelper(SelenideElement widget) {
		this.widget = widget;
	}

	public ElementsCollection ListColumns() {
		widget.$(By.cssSelector(".ant-table-body")).shouldBe(Condition.visible);
		return widget.$(By.cssSelector(".ant-table-body thead")).$(By.cssSelector("tr")).$$(By.cssSelector("th"));
	}

	public ElementsCollection ListRows() {
		widget.$(By.cssSelector(".ant-table-tbody")).shouldBe(Condition.exist);
		return widget.$(By.cssSelector(".ant-table-tbody")).$$(By.tagName("tr"));
	}

	public ElementsCollection ListColumnsInRow(SelenideElement row) {
		return row.$$(By.tagName("td"));
	}

	public Long FindRowId(String columnName, String columnValue) {
		return Long.valueOf(FindRow(columnName, columnValue).getAttribute("data-row-key"));
	}

	public SelenideElement FindRow(Long rowId) {
		widget.$(By.cssSelector(".ant-table-body")).shouldBe(Condition.visible);
		widget.$(By.cssSelector(".ant-table-tbody")).$$(By.cssSelector("tr"))
				.shouldBe(CollectionCondition.sizeGreaterThan(0));
		return widget.$(By.cssSelector(".ant-table-tbody")).$$(By.tagName("tr"))
				.findBy(Condition.attribute("data-row-key", rowId.toString()));
	}

	public SelenideElement FindRow(String columnName, String columnValue) {
		widget.$(By.cssSelector(".ant-table-body")).shouldBe(Condition.visible);
		widget.$(By.cssSelector(".ant-table-tbody")).$$(By.cssSelector("tr"))
				.shouldBe(CollectionCondition.sizeGreaterThan(0));
		return widget.$(By.cssSelector(".ant-table-tbody")).$$(By.tagName("tr"))
				.stream()
				.filter(r -> FindColumnInRow(columnName, r).getText().equals(columnValue))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("no row with columnValue found"));
	}

	public SelenideElement FindColumnInRow(String columnName, SelenideElement row) {
		widget.$(By.cssSelector(".ant-table-body")).shouldBe(Condition.visible);
		return row.$$(By.tagName("td")).get(getIndexColumn(columnName));
	}

	public Integer getIndexColumn(String columnName) {
		$(By.cssSelector(".ant-table-body")).shouldBe(Condition.visible);
		List<String> tableColumns = widget.$(By.cssSelector(".ant-table-thead"))
				.$$(By.tagName("th")).texts();
		return tableColumns.indexOf(columnName);
	}

}
