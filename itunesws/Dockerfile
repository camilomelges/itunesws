FROM maven:3.6.3-openjdk-15 AS download

ARG PROJECT_VERION
ARG PROJECT_CI_JOB_TOKEN
ARG PROJECT_CI_SERVER_URL
ARG PROJECT_CI_PROJECT_ID

RUN echo $PROJECT_VERION

ENV CI_JOB_TOKEN=$PROJECT_CI_JOB_TOKEN
ENV CI_SERVER_URL=$PROJECT_CI_SERVER_URL
ENV CI_PROJECT_ID=$PROJECT_CI_PROJECT_ID

COPY pom.xml /home/app/
COPY ci_settings.xml /home/app/

RUN cd /home/app/ && mvn org.apache.maven.plugins:maven-dependency-plugin:2.8:get -s ci_settings.xml -Dartifact=com.rafamilo:itunesws:$PROJECT_VERION -Ddest=app.jar

FROM openjdk:15-alpine
COPY --from=download /home/app/app.jar /home/app/app.jar
ENTRYPOINT ["java", "-jar", "/home/app/app.jar"]
