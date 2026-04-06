FROM openjdk:21-jdk-slim
EXPOSE 9081
MAINTAINER Xavier Sarmiento
# Set Image NAME and Tag
LABEL name="banking-core" image="banking-core" version="0.0.1" authors="Xavier Sarmiento"
# Set Name
# Expose port 8080
COPY target/banking-core-0.0.1-SNAPSHOT.jar corfinance.jar
#ADD ${JAR_FILE} app.jar
#COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/corfinance.jar"]
# Set the timezone environment variable
# ENV TZ=America/New_York