# YOU HAVE TO BIND MOUNTED VOLUMES TO THIS CONTAINER
# Mount C:\Users\Your Name\.m2 to /root/.m2
# Mount C:\Users\Your Name\Documents\GitHub\nomad-realms-server to /root/nomad-realms-server
# Sample docker run command:
# docker run -it -v $HOME/.m2/repository:/root/.m2/repository -v $HOME\Documents\GitHub\nomad-realms-server:/root/nomad-realms-server --name nomad-realms-server-test nomad-realms-server-test:0.0.0

FROM maven:3-eclipse-temurin-17-alpine

WORKDIR /root/nomad-realms-server

CMD ["mvn", "clean", "install", "exec:java"]
