FROM openjdk:17-jdk-oracle
MAINTAINER TONY0808
COPY ./target/bookapi-0.0.1-SNAPSHOT.jar bookapp.jar
ENTRYPOINT ["java", "-jar", "bookapp.jar"]
EXPOSE 8080