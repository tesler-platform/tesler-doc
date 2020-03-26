FROM openjdk:8
ADD target/tesler-doc-app-exec.jar /app/tesler-doc-app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "tesler-doc-app.jar"]