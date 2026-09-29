FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

FROM eclipse-temurin:17-jre
WORKDIR /app
RUN useradd -r -u 1001 spring
COPY --from=build /app/target/app.jar app.jar
USER spring
EXPOSE 8082
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]