# =====================================================
# Dockerfile para Guia de Remision API
# Multi-stage build para imagen optimizada
# =====================================================

# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copiar archivos de Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Dar permisos de ejecucion
RUN chmod +x mvnw

# Descargar dependencias (cache layer)
RUN ./mvnw dependency:go-offline -B || true

# Copiar codigo fuente
COPY src ./src

# Construir aplicacion (sin tests para CI/CD mas rapido)
RUN ./mvnw clean package -DskipTests -B

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crear usuario no-root para seguridad
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Copiar JAR desde stage de build
COPY --from=build /app/target/*.jar app.jar

# Cambiar ownership del archivo
RUN chown appuser:appgroup app.jar

# Usar usuario no-root
USER appuser

# Puerto de la aplicacion
EXPOSE 8080

# Variables de entorno
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
ENV SPRING_PROFILES_ACTIVE="azure"

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Ejecutar aplicacion
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
