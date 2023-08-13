FROM openjdk:11
EXPOSE 8080

ARG HOME_DIR=/usr/app

WORKDIR $HOME_DIR
COPY . ./
COPY ./src/main/resources/application.yml.example ./src/main/resources/application.yml
RUN ./gradlew bootJar
RUN ls ./build/libs/

ARG JAR_FILE=./build/libs/*.jar
RUN cp ${JAR_FILE} ./app.jar
# 소스 파일 삭제
RUN rm -R ./src

ENTRYPOINT ["java", "-jar", "app.jar"]
