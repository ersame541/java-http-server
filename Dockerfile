FROM cimg/openjdk:17.0

WORKDIR /app

COPY . /app

RUN ant -version

CMD ["java", "-jar", "dist/JavaApplication1.jar"]
