FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /build

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B -q dependency:go-offline

COPY src/ src/
RUN ./mvnw -B -q clean package -DskipTests

RUN mkdir -p extracted && \
    java -Djarmode=tools -jar target/*.jar extract --layers --launcher --destination extracted

FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

RUN addgroup -S taskpulse && adduser -S taskpulse -G taskpulse

COPY --from=build --chown=taskpulse:taskpulse /build/extracted/dependencies/ ./
COPY --from=build --chown=taskpulse:taskpulse /build/extracted/spring-boot-loader/ ./
COPY --from=build --chown=taskpulse:taskpulse /build/extracted/snapshot-dependencies/ ./
COPY --from=build --chown=taskpulse:taskpulse /build/extracted/application/ ./

USER taskpulse
EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "org.springframework.boot.loader.launch.JarLauncher"]
