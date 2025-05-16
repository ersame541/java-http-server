FROM cimg/openjdk:17.0

WORKDIR /app

COPY . /app

EXPOSE 8180

CMD ["java", "-jar", "dist/JavaApplication1.jar"]
