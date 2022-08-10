# Run the following command from your GitHub folder (the parent folder of nomad-realms-server).
# You can replace 0.0.0 with whatever tag you want.
# docker build -t nomad-realms-server:0.0.0 -f .\nomad-realms-server\Dockerfile .

FROM maven:3-eclipse-temurin-17-alpine

WORKDIR /root/nomad-realms-server
COPY . .

CMD ["mvn", "install", "exec:java"]
