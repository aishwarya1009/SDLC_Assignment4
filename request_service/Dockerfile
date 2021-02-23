FROM gradle:6.6.1-jre11 AS build-env

# Set build arguments from build-image.sh
ARG PORT
ARG MYSQL_URL
ARG MYSQL_USER
ARG MYSQL_PASSWORD

# Set the working directory to /home
WORKDIR /home

COPY --chown=gradle:gradle . ./

# Compile the application.
RUN gradle build --no-daemon

FROM openjdk:11.0.7-jre-slim

# Set the working directory to /home
WORKDIR /home

# Need this to find jar files
ARG PROJECT_NAME

# Copy the compiled files over.
COPY --from=build-env /home/build/libs/${PROJECT_NAME}-*-all.jar /home/${PROJECT_NAME}.jar

# Expose the port
EXPOSE ${PORT}

# Starts the java app
CMD java -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar ${PROJECT_NAME}.jar
