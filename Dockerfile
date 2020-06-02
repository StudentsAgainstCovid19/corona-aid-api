#FROM maven:3.6.3-openjdk-11 as mvn_build

#RUN mkdir -p /application
#WORKDIR /application
#COPY pom.xml /application
#COPY src /application/src
#RUN mvn -B -f pom.xml clean package -DskipTests

#FROM openjdk:11
#COPY --from=mvn_build /application/target/*.jar application.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "application.jar"]

FROM gcr.io/distroless/java:11
COPY target/*.jar application.jar
CMD ["application.jar"]
EXPOSE 8080