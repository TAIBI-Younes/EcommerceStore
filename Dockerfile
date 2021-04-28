FROM adoptopenjdk/openjdk11:ubi
VOLUME /tmp

ARG JAR_FILE
COPY target/EcommerceStore-0.0.1-SNAPSHOT.jar EcommerceStore.jar
ENTRYPOINT ["java","-jar","/EcommerceStore.jar"]