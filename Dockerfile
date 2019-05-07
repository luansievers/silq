FROM openjdk:8-slim

ENTRYPOINT ["/usr/bin/java", "-jar", "silq2-2.3.2.war"]

ADD target/silq2-2.3.2.war silq2-2.3.2.war
