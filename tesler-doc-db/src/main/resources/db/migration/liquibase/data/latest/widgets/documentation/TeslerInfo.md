# Tesler info
Tesler is a framework that allows you to quickly develop applications using a full power of existing enterprise Java solutions.

Main Tesler features are: 

1. Abstraction of a business component to simplify access to data;

2. A fixed contract with a user interface called Tesler-UI, which allows you to create typical interface elements in the form of Json files;

3. A single DAO layer, simplifying work with JPA;

4. The SQL engine that allows you to quickly generate typical business components in the application;

5. Abstraction of the task scheduler to create background tasks;

6. Built-in BPM - an engine that allows you to create business processes.

# Archetypes info

Archetypes are stored in `tesler-archetypes` folder. There are 3 Tesler archetypes: 

1. `tesler-archetypes/tesler-base-archetype` - A project generated from this archetype will only contain the configuration files for spring, maven, and liquibase(i.e. no business logic)

2. `tesler-archetypes/tesler-vanilla-archetype` - In addition to the configuration files, generated project will contain some example screens. All backend business logic on these screens is stored in the `tesler-vanilla` module.

3. `tesler-archetypes/tesler-workflow-archetype` - In addition to the configuration files, generated project will contain example services for Tesler Workflow (State Machine) plugin - `tesler-plugins/tesler-workflow-*`.


# Structure of generated project

Generated project contains several modules:

1. `<YourProjectName>-app` - This module contain Spring Boot configuration beans, main class, and application.properties files. The main result of compiling this module is the -exec jar, which is the Spring Boot JAR Application. 

2. `<YourProjectName>-base` - This module defines the versions of all used dependencies and plugins (in <dependencyManagement> and <pluginManagement> sections). Also it defines some maven profiles that Application uses. This module does not have any classes inside.

3. `<YourProjectName>-bom` - This module is a kind of maven "bill of materials" for application. It defines the versions of all the artifacts that will be created in the project.

4. `<YourProjectName>-db` - Module for initializing the database. Uses liquibase as the main library. You can run database initializing manually using liquibase-maven-plugin specified in module pom.xml, or with Spring Liquibase Autoconfiguration, that starts initialization when application starts.

5. `<YourProjectName>-it` - Module for integration tests. At the moment this module is generated without any code.

6. `<YourProjectName>-model` - Module for JPA entities. Hibernate uses entities to create DB queries.

7. `<YourProjectName>-source` - Module for business logic. All source code that modifies business data should be defined in this module.

8. `<YourProjectName>-tests` - Module for unit tests. At the moment this module is generated only with one abstract TestCase class.

# Current artifact issues

1. At the moment, archetype does not contain UI part of Tesler. The solution is to copy the interface from the documentation project.

