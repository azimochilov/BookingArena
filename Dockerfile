FROM vegardit/graalvm-maven:latest-java17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn -f /app/pom.xml clean package

FROM openjdk:17-oracle
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
COPY --from=builder /app/.env /app/.env
COPY --from=builder /app/images /app/images
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/*.jar"]