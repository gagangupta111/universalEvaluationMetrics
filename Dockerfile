FROM openjdk:8
ADD target/spring-boot-uem.jar spring-boot-uem.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "spring-boot-uem.jar"]
