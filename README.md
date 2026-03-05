# Basic-Microservice
This a test microservice implemented with Java 21 and Spring Boot to practice the Microservices implementation.

## How to run on Docker
Build docker image

`docker build -t basic-microservice .`

Run docker image

`docker run --name basic-microservice -d -p 8080:8080 basic-microservice:latest`