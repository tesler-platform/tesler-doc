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

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class PickListWidget {

	private SelenideElement widget;

	private ListHelper helper;

	public PickListWidget() {
		widget = $(By.cssSelector("div[class^='ant-table-wrapper PickListPopup__table']"));
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

	public SelenideElement FindColumnInRow(String columnName, SelenideElement row) {
		return helper.FindColumnInRow(columnName, row);
	}

}
