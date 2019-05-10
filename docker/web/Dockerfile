FROM maven:3.6.1-jdk-8-slim as BUILD
COPY ./src /usr/src/myapp/src
COPY ./pom.xml /usr/src/myapp
RUN mvn -f /usr/src/myapp/pom.xml clean package -DskipTests

FROM openjdk:8-slim
COPY --from=BUILD /usr/src/myapp/target/silq2-2.3.2.war silq2.war
ENTRYPOINT ["/usr/bin/java", "-jar", "silq2.war"]

#FROM openjdk:8-slim
#
#ADD silq2-2.3.2.war silq2-2.3.2.war
#ENTRYPOINT ["/usr/bin/java", "-jar", "silq2-2.3.2.war"]

