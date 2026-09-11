# Etapa 1: Compilación
FROM gradle:8.14-jdk21 AS build

WORKDIR /app

COPY . .

RUN ./gradlew clean bootJar --no-daemon


# Etapa 2: Ejecución
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]