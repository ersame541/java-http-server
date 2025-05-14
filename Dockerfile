FROM openjdk:17-jdk-slim

# Set the ANT_VERSION environment variable
ENV ANT_VERSION=1.10.3

WORKDIR /app

# Copy the project files into the container
COPY . /app

# Install dependencies and Ant
RUN apt-get update && apt-get install -y wget \
    && wget https://downloads.apache.org/ant/binaries/apache-ant-${ANT_VERSION}-bin.tar.gz \
    && tar -xzf apache-ant-${ANT_VERSION}-bin.tar.gz -C /opt \
    && ln -s /opt/apache-ant-${ANT_VERSION} /opt/ant \
    && ln -s /opt/ant/bin/ant /usr/bin/ant

# Verify Ant installation
RUN ant -version

CMD ["java", "-jar", "dist/JavaApplication1.jar"]
