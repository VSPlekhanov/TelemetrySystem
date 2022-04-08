FROM gradle:jdk11-alpine AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# TODO separate build scripts for docker and local development
RUN gradle build


FROM openjdk:11-slim
WORKDIR /app
COPY --from=builder /home/gradle/src/web/build/libs/web.jar ./app.jar
COPY ./wait-for-it.sh /usr/bin/
EXPOSE 8080
