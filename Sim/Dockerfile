FROM maven:3.9-eclipse-temurin-17


WORKDIR /app

COPY . .

RUN mvn dependency:go-offline

EXPOSE 8080

CMD ["mvn", "spring-boot:run"]
