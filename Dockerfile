FROM eclipse-temurin:25-ea-jdk-alpine AS build
RUN apk add --no-cache maven
COPY docker/settings.xml /root/.m2/settings.xml
WORKDIR /app
COPY /src /app/src
COPY /pom.xml /app
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip -s /root/.m2/settings.xml

FROM eclipse-temurin:25-ea-jdk-alpine
EXPOSE 8082
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar --server.port=${PORT:-8082}"]
