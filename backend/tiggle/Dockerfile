FROM openjdk:11
EXPOSE 8080

COPY ./src/main/resources/application.yml.example ./src/main/resources/application.yml
COPY . /usr/app

WORKDIR /usr/app
RUN ./gradlew bootJar

ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
