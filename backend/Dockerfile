FROM maven:3.9.5 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package


FROM amazoncorretto:21.0.1-alpine3.18

ARG JAR_FILE=backend-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","backend-0.0.1-SNAPSHOT.jar"]