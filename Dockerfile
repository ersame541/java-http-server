FROM cimg/openjdk:17.0

WORKDIR /app

COPY . /app

CMD ["java", "-jar", "dist/JavaApplication1.jar"]
