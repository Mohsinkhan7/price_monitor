FROM openjdk:11

COPY jar/price_monitor-0.0.1-SNAPSHOT.jar /opt/myapp/

WORKDIR /opt/myapp/

ENTRYPOINT ["java", "-jar", "/opt/myapp/price_monitor-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
