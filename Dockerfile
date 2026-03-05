FROM eclipse-temurin:21-jre

RUN mkdir -p /opt/basic-microservice

WORKDIR /opt/basic-microservice

COPY target/*.jar basic-microservice.jar

CMD ["java", "-jar", "basic-microservice.jar"]