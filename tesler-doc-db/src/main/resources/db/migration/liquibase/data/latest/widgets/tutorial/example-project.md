# An example project that includes 2 entities

Using this instruction, you will get an example project that consists of two related entities and has two views.

Pre-requisites:

 1. Use this guide to *[install Tesler framework](https://github.com/tesler-platform/tesler-doc)*
    
 # Step 1: Creating Entity
 
 - Path from Source root - io/tesler/entity
 - Extends BaseEntity for provide visions, Data consistency for co-authoring.
```java
public class Bank extends BaseEntity{

    @Column name
    // ...
}
 ```
 - You can look at an example of a class *[Bank.java](https://github.com/tesler-platform/tesler-doc/blob/master/tesler-doc-model/src/main/java/io/tesler/entity/Bank.java)*
 
 # Step 2: Creating EntityDTO
 
 - Path from Source root - io/tesler/dto
 - Extends DataResponseDTO for provide visions, Data consistency for co-authoring.
```java
public class BankDTO extends DataResponseDTO {

    @SearchParameter
    // ...
}
 ```
- You can look at an example of a class *[BankDTO.java](https://github.com/tesler-platform/tesler-doc/blob/master/tesler-doc-source/src/main/java/io/tesler/dto/BankDTO.java)*

 
 # Step 3: Creating EntityService Interface
 
 - Path from Source root - io/tesler/service/meta
 - Extends ResponseService interface , in the parameters of which indicate created EntityDTO and Entity for mapping them
 ```java
 public interface BankService extends ResponseService<BankDTO, Bank> {
    // ...
 }
  ```
 - You can look at an example of a class *[BankService.java](https://github.com/tesler-platform/tesler-doc/blob/master/tesler-doc-source/src/main/java/io/tesler/service/BankService.java)*
 
  # Step 4: Creating EntityService implementation
 
 - Path from Source root - io/tesler/service/impl
 - The class contains the logic of actions on entity instance (create, update, delete)
 - class must extends abstract class VersionAwareResponseService and implements interface BankService
```java
public class BankServiceImpl extends VersionAwareResponseService<BankDTO, Bank> implements BankService {
    // ...
}
```
 - You can look at an example of a class *[BankServiceImpl.java](https://github.com/tesler-platform/tesler-doc/blob/master/tesler-doc-source/src/main/java/io/tesler/service/impl/BankServiceImpl.java)*
 
 # Step 5: Creating EntityMetaBuilder
 
- Path from Source root - io/tesler/service/meta
- class must extends abstract class FieldMetaBuilder
```java
public class BankFieldMetaBuilder extends FieldMetaBuilder<BankDTO> {
    // ...
}
```

In this class you must override 2 abstract methods: **buildRowDependentMeta** and **buildIndependentMeta**

Сonsider these methods in more detail:

-----------------------------------------
### **BuildRowDependentMeta** 

This method describes **how** you can edit **directly** selected field (editing, available values and etc.)

Method has a RowDependentFieldsMeta object in parameters (name parameter - "field")
```java
@Override
public void buildRowDependentMeta(RowDependentFieldsMeta<BankDTO> fields,
    InnerBcDescription innerBcDescription, Long rowId, Long parRowId) {
    // ...
}
```
We can use the methods of the class RowDependentFieldsMeta

Consider a few examples from the [BankFieldMetaBuilder.java](https://github.com/tesler-platform/tesler-doc/blob/master/tesler-doc-source/src/main/java/io/tesler/service/meta/BankFieldMetaBuilder.java) class: 

- Available fields for editing (method ***setEnabled***)
```java
fields.setEnabled(
    name, // name of field
    activeProjectsAmount, // name of field
    // ...
);
```

- Required fields for editing (method ***setRequired***)

```java
fields.setRequired(name);
```

- Set link (drilldown) to the selected field (method ***setDrilldown***)

```java
fields.setDrilldown(
    name, // name of field
    DrillDownType.INNER, // type of drilldown
    "/screen/components/view/form/" + TESLERDOCServiceAssociation.bankDoc + "/" + rowId //path of drilldown
);
```

-------------------------------------------
### **BuildIndependentMeta**

This method describes actions can be done **regardless** of the field itself (filtering, force active and etc.)

Method has a FieldsMeta object in parameters (name parameter - "fieldsMeta")
```java
@Override
public void buildIndependentMeta(FieldsMeta<BankDTO> fieldsMeta, InnerBcDescription innerBcDescription, Long aLong) {
    // ...
}
```

We can use the methods of the class FieldsMeta 

Consider a few examples from the [BankFieldMetaBuilder.java](https://github.com/tesler-platform/tesler-doc/blob/master/tesler-doc-source/src/main/java/io/tesler/service/meta/BankFieldMetaBuilder.java) class: 

- Fields with filtering available (method ***enableFilter***)
```java
fieldsMeta.enableFilter(
    name,                   //name of field
    activeProjectsAmount,   //name of field
    // ...
);
```

- Set all available dictionary values ​​for filtering the selected field (method ***setAllFilterValuesByLovType***)
```java
fieldsMeta.setAllFilterValuesByLovType(country, TDDictionaryType.COUNTRY);
```

# Step 6: Creating Child Entity
 
- Use steps 1-5 of this guide   

# Step 7: Creating Entity dependency and creating bc for Entity's

- To create widgets on entities, we will use the [ServiceAssociation.java](https://github.com/tesler-platform/tesler-doc/blob/master/tesler-doc-source/src/main/java/io/tesler/crudma/config/TESLERDOCServiceAssociation.java)
- Example of creating a Business Component for entity (without parent-child relationship)
 ```java
 public enum TESLERDOCServiceAssociation implements EnumBcIdentifier {
    // ... 
    bank(BankService.class), // there we give the name Business Component(bank) for the entity (BankService)
    // ...
}
 ```
- **ServiceAssociation** allows us to build a parent-child relationship for selected entities
 ```java
 public enum TESLERDOCServiceAssociation implements EnumBcIdentifier {
    // ... 
    bank(BankService.class),                                        // here we declared a business component-"bc" (bank) that does not have a parent
        linkBankEmployee(bank,LinkBankEmployeeService.class),       // give the name bc (linkBankEmployee) for the entity (LinkBankEmployeeService) is a child for bc (bank) 
            linkBankEmployeeAssoc(bank, EmployeeService.class),     // give the name bc (linkBankEmployeeAssoc) for the entity (EmployeeService) is a child for bc (bank) 
    // ...
}
 ```
- Code in  must be between annotation @formatter:off and @formatter:on
 ```java
public enum TESLERDOCServiceAssociation implements EnumBcIdentifier {
	// @formatter:off
	bank(BankService.class),
		linkBankEmployee(bank,LinkBankEmployeeService.class),
			linkBankEmployeeAssoc(bank, EmployeeService.class),
	// @formatter:on
}
 ```
# Step 8: Creating Screen, View and Widgets

### **Screen**
- ***Screen*** is a navigation container that contains view elements. 

*example.screen.json*:
```ts
{
  "name": "example",
  "title": "Example",
  "primaryViewName": "banklist",
  "primaryViews": [
    "banklist"
  ],
  "navigation": {
    "menu": [
      {
        "title": "Banks",
        "child": [
          {
            "viewName": "banklist"
          },
          {
            "viewName": "bankcard"
          }
        ]
      }
    ]
  }
}
```

- More information about menu and navigation you can find at *[Screen (Major concepts)](#/screen/getting-started/view/screen/)*
-------------------------------------------
### **View**
- ***View*** is UI component that binds to URL and displayed single application page
- View elements contain widgets

*[banklist.view.json](https://github.com/tesler-platform/tesler-doc/blob/master/tesler-doc-db/src/main/resources/db/migration/liquibase/data/latest/screens/bank/views/banklist.view.json)* from example.screen:
```ts
{
  "name": "banklist",
  "title": "Banks",
  "template": "DashboardView",
  "url": "/screen/example/view/banklist",
  "widgets": [
    {
      "widgetName": "SecondLevelMenu",
      "position": 0,
      "gridWidth": 2
    },
    {
      "widgetName": "bankList",
      "position": 1,
      "gridWidth": 2
    }
  ]
}
```

- For more examples, you can also check *[bankcard.view.json](https://github.com/tesler-platform/tesler-doc/blob/master/tesler-doc-db/src/main/resources/db/migration/liquibase/data/latest/screens/bank/views/bankcard.view.json)*
- More information about view API: *[View (Major concepts)](#/screen/getting-started/view/view/)*

-------------------------------------------

### **Widget**
- ***Widget*** is is UI component that works with user information according to the widget type
- Widget elements get information from DTO-objects
- Basic widgets types: *[List](#/screen/components/view/list/)*, *[Form](#/screen/components/view/form/)*
- Basic pop-up widgets types: *[AssocListPopup](#/screen/components/view/assocListPopup/)* and *[PicklistPopup](#/screen/components/view/pickListPopup/)*

*[linkBankEmployee.widget.json](https://github.com/tesler-platform/tesler-doc/blob/develop/tesler-doc-db/src/main/resources/db/migration/liquibase/data/latest/widgets/example/linkBankEmployee.widget.json)* from banklist.view:
```ts
{
  "name": "linkBankEmployee",
  "title": "",
  "type": "List",
  "bc": "linkBankEmployee",
  "showCondition": [],
  "fields": [
    {
      "title": "Name",
      "key": "employee",
      "type": "input"
    },
    {
      "title": "Success Rate",
      "key": "successRate",
      "type": "input"
    },
    {
      "title": "Position",
      "key": "position",
      "type": "input"
    }
  ],
  "axisFields": [],
  "chart": [],
  "options": {
    "actionGroups": {
      "include": [
        "associate"
      ]
    },
    "layout": {}
  }
}
```
This widget has an associated pop-up child-widget *[linkBankEmployeeAssoc.widget.json](https://github.com/tesler-platform/tesler-doc/blob/develop/tesler-doc-db/src/main/resources/db/migration/liquibase/data/latest/widgets/example/linkBankEmployeeAssoc.widget.json)*:
```ts
{
  "name": "linkBankEmployeeAssoc",
  "title": "Employees related to bank",
  "type": "AssocListPopup",
  "bc": "linkBankEmployeeAssoc",
  "showCondition": [],
  "fields": [
    {
      "title": "Name",
      "key": "name",
      "type": "input"
    },
    {
      "title": "Success Rate",
      "key": "successRate",
      "type": "input"
    },
    {
      "title": "Position",
      "key": "position",
      "type": "input"
    }
  ],
  "axisFields": [],
  "chart": []
}
``` 
- Also check *[bankCard.widget.json](https://github.com/tesler-platform/tesler-doc/blob/master/tesler-doc-db/src/main/resources/db/migration/liquibase/data/latest/widgets/example/bankCard.widget.json)* for wide options and actions example
- Additional information about widgets API: *[Widget (Major concepts)](#/screen/getting-started/view/widget/)*
