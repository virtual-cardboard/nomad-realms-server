# Run the following command from your GitHub folder (the parent folder of nomad-realms-server).
# You can replace 0.0.0 with whatever tag you want.
# docker build -t nomad-realms-server:0.0.0 -f .\nomad-realms-server\Dockerfile .

FROM openjdk:8-jdk-alpine

RUN apk update
RUN apk add -y maven

WORKDIR /usr/src/app/
COPY ./derealizer ./derealizer
RUN mvn -f derealizer/pom.xml install
COPY ./lwjgl-game-engine ./lwjgl-game-engine
RUN mvn -f lwjgl-game-engine/pom.xml install
COPY ./debugui ./debugui
RUN mvn -f debugui/pom.xml install
COPY ./nomad-realms-common ./nomad-realms-common
RUN mvn -f nomad-realms-common/pom.xml install

WORKDIR /usr/src/app/nomad-realms-server
COPY ./nomad-realms-server .
RUN mvn install

CMD ["mvn", "exec:java"]
