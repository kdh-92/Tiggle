FROM openjdk:11
EXPOSE 8080

ARG HOME_DIR=/usr/app
ARG FILE_NAME='tiggle-auth-gateway-0.0.1-SNAPSHOT.jar'

# application.yml.example은 일부 정보가 채워져 있지 않기 application.yml을 따로 생성하지 않는다 (자료실의 application.yml을 사용하여 빌드해야 함)
WORKDIR $HOME_DIR
COPY . ./
COPY ./src/main/resources/application.yml.example ./src/main/resources/application.yml

RUN ./gradlew bootJar
RUN ls ./build/libs/

ARG JAR_FILE=./build/libs/$FILE_NAME
RUN cp ${JAR_FILE} ./app.jar
# 소스 파일 삭제
RUN rm -R ./src

ENTRYPOINT ["java", "-jar", "app.jar"]
