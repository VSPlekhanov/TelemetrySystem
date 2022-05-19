FROM openjdk:11
COPY agent/build/libs/agent-1.0-SNAPSHOT.jar .
COPY .env .
RUN java -jar agent-1.0-SNAPSHOT.jar