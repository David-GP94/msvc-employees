# Imagen base de Java (OpenJDK 17)
FROM openjdk:17-jdk-slim
LABEL authors="david.garcia"

# Carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copiar el jar compilado
COPY target/msvc-employees-0.0.1-SNAPSHOT.jar app.jar

# Puerto expuesto
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
