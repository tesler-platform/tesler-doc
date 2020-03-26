# Hello world in Tesler framework

Using this instruction
you will obtain a classic Hello World application, ready to use all features of the Tesler framework.

Pre-requisites:

1. An (IDE)

2. A Java™ Development Kit (JDK)

3. Maven

4. One of currently supported by Tesler databases:

- Postgresql 9.6.15 or later;

- Oracle 11g or later


# Step 1: Creating project from archetype

To create a Tesler project you should use Maven archetype for it. If you have a connection to Maven Central, just use the following command:

```
mvn archetype:generate                               
  -DarchetypeGroupId=io.tesler                       
  -DarchetypeArtifactId=tesler-base-archetype        
  -DarchetypeVersion=<latest.tesler.version>         
  -DgroupId=<my.groupid>                             
  -DartifactId=<my-artifactId>
```
You may also run this command from a new empty Maven project. Maven is going to ask you about starting version, name of your project and some more properties.

Another way is to add Maven archetype to the project and create a new one from it.

More information about maven archetypes can be found here: https://maven.apache.org/guides/introduction/introduction-to-archetypes.html


# Step 2: Build and run an application

The project generated from the archetype can be built using the following command: 

`mvn clean package`

Before run, you should execute:

`mvn clean install`

Tip: if you are using Postgresql, make sure you added 

`vendorAdapter.setDatabasePlatform(PostgreSQL95Dialect.class.getName());`

in <MyApp>ApplicationConfig.class vendorAdapter method.

You can run Spring Boot application with following command:

`mvn spring-boot:run -f <YourProjectName>-app -PDev,<mavenProfileWithJdbcSettings>`

You should see something like: 

`INFO 5332 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet : Completed initialization in 257 ms`

Possible problem:

As Tesler uses Liquidbase, ValidationFailedException in case of database changes may occur. In this case,
 clearing database may be useful during development:

`mvn liquibase:releaseLocks liquibase:dropAll -P<mavenProfileWithJdbcSettings>`




