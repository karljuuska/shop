FROM openjdk:19
ARG JAR_FILE=docker/shop-1.0.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]