FROM public.ecr.aws/amazoncorretto/amazoncorretto:17.0.13 AS builder

COPY . /src
WORKDIR /src

RUN ./gradlew build -x test --no-daemon

FROM public.ecr.aws/amazoncorretto/amazoncorretto:17.0.13

COPY --from=builder /src/build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-Xms512m", "-Xmx1024m", "-jar", "/app.jar"]