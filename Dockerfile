FROM eclipse-temurin:25-alpine AS build
RUN apk add --no-cache maven
WORKDIR /app
COPY /src /app/src
COPY /pom.xml /app
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip

FROM eclipse-temurin:25-alpine
EXPOSE 80
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar --server.port=${PORT:-80}"]
