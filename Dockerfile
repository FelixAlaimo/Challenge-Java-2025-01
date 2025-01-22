FROM openjdk:17
ADD target/ventas-0.0.1-SNAPSHOT.jar ventasApp.jar
ENTRYPOINT [ "java", "-jar","ventasApp.jar" ]