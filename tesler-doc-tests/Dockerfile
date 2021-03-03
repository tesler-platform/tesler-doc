FROM openjdk:8
ADD target/tesler-doc-tests-1.0-SNAPSHOT.jar /app/tesler-doc-tests.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "tesler-doc-tests.jar"]
