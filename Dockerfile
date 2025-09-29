# ==============================
# Stage 1: Build
# ==============================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar solo pom.xml primero para cache de dependencias
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copiar el código fuente completo
COPY src ./src

# Compilar el proyecto y generar jar
RUN mvn clean package -DskipTests -B

# ==============================
# Stage 2: Runtime
# ==============================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el jar generado desde la etapa de build
COPY --from=build /app/target/msvc-employees-0.0.1-SNAPSHOT.jar app.jar

# Puerto expuesto
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
