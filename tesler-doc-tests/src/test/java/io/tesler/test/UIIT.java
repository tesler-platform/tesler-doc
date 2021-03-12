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

package io.tesler.test;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.tesler.test.widgets.FormWidget;
import io.tesler.test.widgets.ListWidget;
import io.tesler.test.widgets.PickListWidget;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UIIT {

	private SelenideElement LoginInput(int index) {
		return $(By.cssSelector(".ant-form-item-control input"), index);
	}

	private SelenideElement LoginButton = $(By.cssSelector(".ant-form-item-control button"));

	private SelenideElement Menu(String text) {
		return $(By.cssSelector("ul[class^='ant-menu ScreenNavigation__Container']"))
				.$$(By.cssSelector("li[class^='ant-menu-item ScreenNavigation__Item']")).findBy(
						Condition.exactText(text));
	}

	private SelenideElement FirstLevelMenu(String text) {
		return $(By.cssSelector("nav[class^='ViewNavigation__container']"))
				.$$(By.cssSelector("span[class^='ViewNavigation__item']")).findBy(Condition.exactText(text));
	}

	private SelenideElement SecondLevelMenu(String text) {
		return $(By.cssSelector("nav[class^='SecondLevelTabs__container']"))
				.$$(By.cssSelector("div[class$='ant-tabs-tab']")).findBy(Condition.exactText(text));
	}

	private String LargeText = "Etiam nec eros nulla. Suspendisse viverra mollis dolor non iaculis. Duis imperdiet facilisis urna non ultrices. Mauris aliquet dui massa, sit amet iaculis enim blandit nec. Suspendisse tempus nunc imperdiet tellus faucibus feugiat.";

	private String SmallText = "Aliquam quis enim.";

	private DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	private DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

	@Test
	public void TestUI() {

		Configuration.headless = true;
		Configuration.browserSize = "1440x768";

		Login();
		TestInput();
		TestDictionary();
		TestNumber();
		TestDate();
		TestPickList();
		TestFilter();
	}

	private void Login() {
		open("http://localhost:8080");

		LoginInput(0).setValue("Test");
		LoginButton.click();
		$(By.cssSelector(".ant-form-item-control >span"), 2).$(By.cssSelector("span[class^='Login__error']"))
				.shouldBe(Condition.visible);
		$(By.cssSelector(".ant-form-item-control >span"), 2).$(By.cssSelector("span[class^='Login__error']"))
				.shouldHave(Condition.exactText("Unauthorized"));
		LoginInput(0).setValue("vanilla");
		LoginButton.click();
		LoginButton.$(By.cssSelector("i")).shouldBe(Condition.visible.negate(), Duration.ofSeconds(50));

	}

	private void TestInput() {
		//Navigate through the menu
		Menu("Components Overview").click();
		FirstLevelMenu("Fields").click();
		SecondLevelMenu("Input").click();

		//Find List widget
		ListWidget lw = new ListWidget();

		//Check records on List widget
		if (lw.ListRows().isEmpty()) {
			lw.ClickButtonList("Create");
			FormWidget fw = new FormWidget();
			fw.getInput("Name").setValue("Test record");
			fw.clickButtonForm("Save");
			FirstLevelMenu("Fields").click();
			SecondLevelMenu("Input").click();
		}

		//Find Form and List widget
		FormWidget fw = new FormWidget();
		lw = new ListWidget();

		//Checking the status of the Input field
		fw.getInput("Test Input").shouldBe(Condition.readonly.negate());
		fw.getInput("Test Input").shouldHave(Condition.attribute("placeholder", "PlaceholderInput test"));

		//Entering text in Input
		fw.getInput("Test Input").setValue(LargeText);
		assertThat(fw.getInput("Test Input").getAttribute("value")).isEqualTo(LargeText);

		//Moving the focus to another required field
		fw.getInput("Name").setValue("Q");
		fw.getInput("Name").sendKeys(Keys.BACK_SPACE);

		//Click "Save"
		fw.clickButtonForm("Save");

		//Checking the required field
		assertThat($(By.cssSelector(".ant-form-explain")).shouldBe(Condition.visible).getText())
				.isEqualTo("This field is mandatory");

		//Filling in the required field
		fw.getInputwithErrors("Name", "This field is mandatory")
				.setValue("This field is mandatory").pressTab();

		//Find Row in List
		Long rowId = lw.FindRowId("Name", "This field is mandatory");

		//Input and List Checks
		assertThat(fw.getInput("Name").getAttribute("value")).isEqualTo("This field is mandatory");
		assertThat(fw.getInput("Test Input").getAttribute("value")).isEqualTo(LargeText);
		assertThat(lw.FindColumnInRow("Test Input", lw.FindRow(rowId)).getText())
				.isEqualTo(LargeText);

		//Click "Save"
		fw.clickButtonForm("Save");

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Input").click();

		//Input and List Checks
		fw.getInput("Test Input").shouldHave(Condition.attribute("value", LargeText));
		assertThat(lw.FindColumnInRow("Test Input", lw.FindRow(rowId)).getText())
				.isEqualTo(LargeText);

		//Entering text into a cell on a List
		lw.FindColumnInRow("Test Input", lw.FindRow(rowId)).doubleClick();
		lw.FindColumnInRow("Test Input", lw.FindRow(rowId)).$(By.cssSelector("input"))
				.setValue(SmallText);

		//Saving with three dots
		lw.ClickActionThreeDots(lw.FindRow(rowId), "Save");

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Input").click();

		//Input and List Checks
		assertThat(lw.FindColumnInRow("Test Input", lw.FindRow(rowId)).getText())
				.isEqualTo(SmallText);
		assertThat(fw.getInput("Test Input").getAttribute("value")).isEqualTo(SmallText);

		//Clearing values on the List widget
		lw.FindColumnInRow("Test Input", lw.FindRow(rowId)).doubleClick();
		lw.FindColumnInRow("Test Input", lw.FindRow(rowId)).$(By.cssSelector("input")).clear();

		//Moving the focus to another column in the table
		lw.ListColumnsInRow(lw.FindRow(rowId)).last().doubleClick();

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Input").click();

		//Проверки значений в List-виджете и Input
		//Нет автосейва при измении фокуса?
		/*assertThat(FirstRowTable().$$(By.tagName("td")).get(getIndexColumn("Test Input")).getText()).isEmpty();
		assertThat(getInput("Test Input").getAttribute("value")).isEmpty();*/
	}

	private void TestDictionary() {
		SecondLevelMenu("Dictionary").click();

		//Find Form widget and List widget
		FormWidget fw = new FormWidget();
		ListWidget lw = new ListWidget();
		Long rowId = lw.FindRowId("Name", "This field is mandatory");

		//Selecting First value in the dropdown list on Form
		fw.getComboBox("Dictionary").click();
		assertThat(fw.getComboBoxList("Dictionary").size()).isEqualTo(4);
		fw.getComboBoxList("Dictionary").first().click();

		//Click "Save" on the Form
		fw.getInput("Name").setValue("This field is mandatory");
		fw.clickButtonForm("Save");

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Dictionary").click();

		//Dictionary and List checks
		assertThat(fw.getComboBox("Dictionary").$(By.cssSelector(".ant-select-selection-selected-value"))
				.getAttribute("title")).isEqualTo("First value");
		assertThat(lw.FindColumnInRow("Dictionary", lw.FindRow(rowId)).getText())
				.isEqualTo("First value");

		//Removing a selection from a dropdown list on Form
		fw.clearComboBox("Dictionary");

		//Click "Save" on the Form
		fw.clickButtonForm("Save");

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Dictionary").click();

		//Dictionary and List checks
		assertThat(fw.getComboBox("Dictionary").$(By.cssSelector(".ant-select-selection-selected-value"))
				.getAttribute("title")).isEmpty();
		assertThat(lw.FindColumnInRow("Dictionary", lw.FindRow(rowId)).getText()).isEmpty();

		//Selecting the Fourth value in the dropdown list on List
		lw.FindRow(rowId).$$(By.tagName("td")).last().scrollIntoView(true);

		ElementsCollection comboBoxList = lw
				.getComboBoxList(lw.FindColumnInRow("Dictionary", lw.FindRow(rowId)));
		assertThat(comboBoxList.size()).isEqualTo(4);
		comboBoxList.last().click();

		//Saving with three dots
		lw.ClickActionThreeDots(lw.FindRow(rowId), "Save");

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Dictionary").click();

		//Dictionary and List checks
		assertThat(lw.FindColumnInRow("Dictionary", lw.FindRow(rowId)).getText())
				.isEqualTo("Fourth value");
		assertThat(fw.getComboBox("Dictionary").$(By.cssSelector(".ant-select-selection-selected-value"))
				.getAttribute("title")).isEqualTo("Fourth value");

		//Clear a Dictionary on a List
		lw.FindRow(rowId).$$(By.tagName("td")).last().scrollIntoView(true);

		lw.clearComboBoxList(lw.FindColumnInRow("Dictionary", lw.FindRow(rowId)));

		//Moving the focus to another column in the table
		lw.ListColumnsInRow(lw.FindRow(rowId)).last().doubleClick();

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Dictionary").click();

		//Автосейв при смене фокуса на другую колонку?
		//Проверки Dictionary и List
		/*assertThat(getComboBox("Dictionary").$(By.cssSelector(".ant-select-selection-selected-value"))
				.getAttribute("title")).isEmpty();
		assertThat($(By.cssSelector("div[class^=TableWidget]")).$(By.cssSelector(".ant-table-tbody")).$$(By.tagName("tr"))
				.first().$$(By.tagName("td")).get(getIndexColumn("Dictionary")).getText()).isEmpty();*/
	}

	private void TestNumber() {
		SecondLevelMenu("Number").click();

		//Find Form widget and List widget
		FormWidget fw = new FormWidget();
		ListWidget lw = new ListWidget();
		Long rowId = lw.FindRowId("Name", "This field is mandatory");

		//Entering text values in fields on Form
		fw.getInput("Number").setValue("Text");
		fw.getInput("Money").setValue("Text");
		fw.getInput("Percent").setValue("Text");
		fw.getInput("Percent").pressTab();

		//Field checks
		assertThat(fw.getInput("Number").getAttribute("value")).isEqualTo("0");
		assertThat(fw.getInput("Money").getAttribute("value")).isEqualTo("0,00");
		assertThat(fw.getInput("Percent").getAttribute("value")).isEqualTo("0 %");

		//Entering numeric values in fields on Form
		fw.getInput("Number").setValue("-10g000");
		fw.getInput("Money").setValue("01222333.489");
		fw.getInput("Percent").setValue("-998,500");

		//Click "Save" on the Form
		fw.clickButtonForm("Save");

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Number").click();

		//Numbers and List checks
		fw.getInput("Number").shouldHave(Condition.attribute("value", "-10" + "\u00A0" + "000"));
		fw.getInput("Money").shouldHave(Condition.attribute("value", "1" + "\u00A0" + "222" + "\u00A0" + "333,49"));
		//TODO с активным фокусом на поле не происходит округления
		fw.getInput("Percent").shouldHave(Condition.attribute("value", "-998 %"));

		//TODO неверное отображение в List виджете (в режиме просмотра нет сотых долей, знака %)
		/*assertThat(FirstRowTable().$$(By.tagName("td")).get(getIndexColumn("Number")).getText()).isEqualTo("-10"+"\u00A0"+"000");
		assertThat(FirstRowTable().$$(By.tagName("td")).get(getIndexColumn("Money")).getText()).isEqualTo("1"+"\u00A0"+"222"+"\u00A0"+"333,49");
		assertThat(FirstRowTable().$$(By.tagName("td")).get(getIndexColumn("Percent")).getText()).isEqualTo("-998 %");*/
		lw.FindColumnInRow("Number", lw.FindRow(rowId)).shouldHave(Condition.exactText("-10 000"));
		lw.FindColumnInRow("Money", lw.FindRow(rowId)).shouldHave(Condition.exactText("1 222 333"));
		lw.FindColumnInRow("Percent", lw.FindRow(rowId)).shouldHave(Condition.exactText("-998"));

		//Entering values on List
		lw.FindColumnInRow("Number", lw.FindRow(rowId)).doubleClick();
		lw.FindColumnInRow("Number", lw.FindRow(rowId)).$(By.tagName("input")).clear();
		lw.FindColumnInRow("Number", lw.FindRow(rowId)).$(By.tagName("input")).setValue("g");
		lw.FindColumnInRow("Number", lw.FindRow(rowId)).$(By.tagName("input")).setValue("-20000,1");
		lw.FindColumnInRow("Money", lw.FindRow(rowId)).doubleClick();
		lw.FindColumnInRow("Money", lw.FindRow(rowId)).$(By.tagName("input")).clear();
		lw.FindColumnInRow("Money", lw.FindRow(rowId)).$(By.tagName("input")).setValue("g");
		lw.FindColumnInRow("Money", lw.FindRow(rowId)).$(By.tagName("input")).setValue("-7000,126");
		lw.FindColumnInRow("Percent", lw.FindRow(rowId)).doubleClick();
		lw.FindColumnInRow("Percent", lw.FindRow(rowId)).$(By.tagName("input")).clear();
		lw.FindColumnInRow("Percent", lw.FindRow(rowId)).$(By.tagName("input")).setValue("g");
		lw.FindColumnInRow("Percent", lw.FindRow(rowId)).$(By.tagName("input")).setValue("0800,512");

		//Saving with three dots
		lw.ClickActionThreeDots(lw.FindRow(rowId), "Save");

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Number").click();

		//Numbers and List checks
		fw.getInput("Number").shouldHave(Condition.attribute("value", "-20" + "\u00A0" + "000"));
		fw.getInput("Money").shouldHave(Condition.attribute("value", "-7" + "\u00A0" + "000,13"));
		//TODO с активным фокусом на поле не происходит округления
		fw.getInput("Percent").shouldHave(Condition.attribute("value", "800 %"));

		//TODO неверное отображение в List виджете
		/*assertThat(FirstRowTable().$$(By.tagName("td")).get(getIndexColumn("Number")).getText()).isEqualTo("-20"+"\u00A0"+"000");
		assertThat(FirstRowTable().$$(By.tagName("td")).get(getIndexColumn("Money")).getText()).isEqualTo("-7"+"\u00A0"+"000,13");
		assertThat(FirstRowTable().$$(By.tagName("td")).get(getIndexColumn("Percent")).getText()).isEqualTo("801"+"\u00A0"+"%");*/
		assertThat(lw.FindColumnInRow("Number", lw.FindRow(rowId)).getText()).isEqualTo("-20 000");
		assertThat(lw.FindColumnInRow("Money", lw.FindRow(rowId)).getText()).isEqualTo("-7 000");
		assertThat(lw.FindColumnInRow("Percent", lw.FindRow(rowId)).getText()).isEqualTo("800");
	}

	private void TestDate() {
		SecondLevelMenu("Date").click();

		//Find Form widget and List widget
		FormWidget fw = new FormWidget();
		ListWidget lw = new ListWidget();
		Long rowId = lw.FindRowId("Name", "This field is mandatory");

		//Checking that the date is not filled in
		if (!(lw.FindColumnInRow("Date", lw.FindRow(rowId)).getText().isEmpty())) {
			lw.clearDatePicker(lw.FindColumnInRow("Date", lw.FindRow(rowId)));
		}

		//Selecting the current day on Form
		fw.setDateCurrentDay(fw.getInput("Date"));
		LocalDateTime date = LocalDateTime.now();

		//Click "Save" on the Form
		fw.clickButtonForm("Save");

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Date").click();

		//Checking the date completion
		assertThat(fw.getInput("Date").getAttribute("value")).isEqualTo(LocalDate.now().format(DATE_FORMATTER));
		assertThat(lw.FindColumnInRow("Date", lw.FindRow(rowId)).getText())
				.isEqualTo(date.format(DATE_FORMATTER));
		assertThat(lw.FindColumnInRow("Date and Time", lw.FindRow(rowId)).getText())
				.isEqualTo(date.format(DATE_TIME_FORMATTER));

		//Selecting the current day on List
		lw.setDateCurrentDay(lw.FindColumnInRow("Date", lw.FindRow(rowId)));

		//Checking the date completion
		assertThat(lw.FindColumnInRow("Date", lw.FindRow(rowId)).$(By.cssSelector("input")).getValue())
				.isEqualTo(date.format(DATE_FORMATTER));

		//Clear Date picker on List
		lw.clearDatePicker(lw.FindColumnInRow("Date", lw.FindRow(rowId)));

		//Saving with three dots
		lw.ClickActionThreeDots(lw.FindRow(rowId), "Save");

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Date").click();

		//Check Date is empty
		assertThat(fw.getInput("Date").getAttribute("value")).isEmpty();
		assertThat(lw.FindColumnInRow("Date", lw.FindRow(rowId)).getText()).isEmpty();
		assertThat(lw.FindColumnInRow("Date and Time", lw.FindRow(rowId)).getText()).isEmpty();
	}

	private void TestPickList() {
		SecondLevelMenu("Picklist").click();

		//Find Form widget and List widget
		FormWidget fw = new FormWidget();
		ListWidget lw = new ListWidget();
		Long rowId = lw.FindRowId("Name", "This field is mandatory");

		//Clearing the input field
		fw.getInput("Test Input").clear();
		assertThat(fw.getInput("Test Input").getValue()).isEmpty();

		//Calling Popup on Form
		fw.callPickList("Pick List").click();

		//Click on a column in Popup
		PickListWidget pl = new PickListWidget();
		Long plRowId = pl.FindRowId("Name", "This field is mandatory");
		pl.FindColumnInRow("Name", pl.FindRow(plRowId)).click();

		//Click "Save" on the Form
		fw.clickButtonForm("Save");

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Picklist").click();

		//Checking the selected values
		assertThat(fw.getInput("Test input").getValue()).isEqualTo("-20000");
		assertThat(fw.getInput("Pick List").getValue()).isEqualTo("This field is mandatory");
		fw.getInput("Pick List").shouldBe(Condition.readonly);
		assertThat(lw.FindColumnInRow("Test Input", lw.FindRow(rowId)).getText()).isEqualTo("-20000");
		assertThat(lw.FindColumnInRow("Pick List", lw.FindRow(rowId)).getText())
				.isEqualTo("This field is mandatory");

		//Clearing the input field
		fw.getInput("Test Input").clear();
		assertThat(fw.getInput("Test Input").getValue()).isEmpty();

		//Calling Popup and selecting values on List
		lw.ListColumnsInRow(lw.FindRow(rowId)).last().scrollIntoView(true);
		lw.FindColumnInRow("Pick List", lw.FindRow(rowId)).doubleClick();
		lw.getPickList(lw.FindColumnInRow("Pick List", lw.FindRow(rowId)));
		pl.FindColumnInRow("Name", pl.FindRow(plRowId)).click();

		//Saving with three dots
		lw.ClickActionThreeDots(lw.FindRow(rowId), "Save");

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Picklist").click();

		//Checking the selected values
		assertThat(fw.getInput("Test input").getValue()).isEqualTo("-20000");
		assertThat(fw.getInput("Pick List").getValue()).isEqualTo("This field is mandatory");
		fw.getInput("Pick List").shouldBe(Condition.readonly);
		assertThat(lw.FindColumnInRow("Test Input", lw.FindRow(rowId)).getText()).isEqualTo("-20000");
		assertThat(lw.FindColumnInRow("Pick List", lw.FindRow(rowId)).getText())
				.isEqualTo("This field is mandatory");

		//Clearing the PickList value on Form
		fw.clearPickList("Pick List");

		//Click "Save" on the Form
		fw.clickButtonForm("Save");

		//Navigating tabs
		SecondLevelMenu("Fields").click();
		SecondLevelMenu("Picklist").click();

		//Check PickList value is empty
		assertThat(fw.getInput("Test input").getValue()).isEmpty();
		assertThat(fw.getInput("Pick List").getValue()).isEmpty();
		assertThat(lw.FindColumnInRow("Test Input", lw.FindRow(rowId)).getText()).isEmpty();
		assertThat(lw.FindColumnInRow("Pick List", lw.FindRow(rowId)).getText()).isEmpty();
	}

	private void TestFilter() {
		//Navigate through the menu
		SecondLevelMenu("Fields").click();

		//Find List widget
		ListWidget lw = new ListWidget();

		//Open Filter on column
		//Set a value and apply a filter
		lw.setFilterInput("Name", "This field is mandatory");

		//Checking strings for compliance
		lw.ListRows().last().scrollIntoView(true);
		assertThat(lw.ListRows()
				.stream()
				.allMatch(r -> r.$$(By.tagName("td")).get(lw.getIndexColumn("Name")).getText()
						.equals("This field is mandatory"))).isTrue();

		//Clear Filter on column
		lw.clearFilterInput("Name");

		//Open Filter on column
		//Set a random value and apply a filter
		lw.setFilterInput("Name", "Random_text_3_P5LbW");

		//Checking strings for compliance (The waiting is empty)
		$(By.cssSelector(".ant-table-tbody")).shouldBe(Condition.exist);
		assertThat(lw.ListRows()
				.stream()
				.allMatch(r -> r.$$(By.tagName("td")).get(lw.getIndexColumn("Name")).getText().isEmpty()
				)).isTrue();

		//Click "Clear all filters"
		lw.clearAllFilters();

		//Checking strings for compliance (The waiting is not null)
		lw.ListRows().shouldBe(CollectionCondition.sizeGreaterThan(0));
		assertThat(lw.ListRows()
				.stream()
				.allMatch(r -> r.$$(By.tagName("td")).get(lw.getIndexColumn("Name")).getText().isEmpty()
				)).isFalse();
	}

}