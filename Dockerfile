FROM vegardit/graalvm-maven:latest-java17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:17-oracle
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
COPY --from=builder /app/.env /app/.env
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/*.jar"]