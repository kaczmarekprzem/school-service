FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN ./mvnw package

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/school-service-0.0.1-SNAPSHOT.jar school-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/school-service-0.0.1-SNAPSHOT.jar"]