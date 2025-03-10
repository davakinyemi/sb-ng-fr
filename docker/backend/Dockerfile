# Build stage
FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM amazoncorretto:23
ARG PROFILE=dev
ARG APP_VERSION=1.0.3
ARG APP_VERSION=1.0.3

WORKDIR /app
COPY --from=build /build/target/spring-boot-fr-*.jar /app/

EXPOSE 8088

ENV DB_URL=jdbc:postgresql://postgres-sql-bsn:5432/sb_ng_fr
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}
ENV EMAIL_HOST_NAME=missing_host_name
ENV EMAIL_PORT=missing_port
ENV EMAIL_USER_NAME=missing_user_name
ENV EMAIL_PASSWORD=missing_password

CMD ["sh", "-c", "exec java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} spring-boot-fr-${JAR_VERSION}.jar"]

# Build-command: docker build -t sbngfr/sbngfr:1.0.0 -f ../docker/backend/Dockerfile .