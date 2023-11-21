FROM maven:3.9.5-amazoncorretto-17 as build
COPY . /usr/app
WORKDIR /usr/app
RUN mvn -DskipTests=true clean package

FROM amazoncorretto:17.0.9
COPY --from=build /usr/app/target/*.jar MqttToMongoDB.jar
ENTRYPOINT ["java", "-jar", "/MqttToMongoDB.jar"]
