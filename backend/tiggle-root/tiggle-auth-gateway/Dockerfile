FROM openjdk:11
EXPOSE 8080

ARG HOME_DIR=/usr/app
ARG FILE_NAME='tiggle-auth-gateway-0.0.1-SNAPSHOT.jar'

WORKDIR $HOME_DIR
COPY . ./
RUN ./gradlew bootJar
RUN ls ./build/libs/

ARG JAR_FILE=./build/libs/$FILE_NAME
RUN cp ${JAR_FILE} ./app.jar
# 소스 파일 삭제
RUN rm -R ./src

ENTRYPOINT ["java", "-jar", "app.jar"]
