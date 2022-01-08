FROM openjdk:17.0.1

COPY ./build/libs/app.jar /app.jar

EXPOSE 8080

CMD ["java", "-Dserver.port=8080", "-jar", "/app.jar"]