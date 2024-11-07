# Cache Gradle dependencies
FROM gradle:8.10.2-jdk21 AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle.* settings.gradle* gradle.properties /home/gradle/app/
WORKDIR /home/gradle/app
RUN gradle clean build --no-daemon --parallel -configure-on-demand

# Build Application
FROM gradle:8.10.2-jdk21 AS build
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY . /usr/src/app/
WORKDIR /usr/src/app
RUN gradle buildFatJar --no-daemon -parallel --configure-on-demand

# Create the Runtime Image
FROM openjdk:21-jre-slim AS runtime
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*-all.jar /app/ktor-bazar.jar
ENV JAVA_OPTS="-XX:+UseG1GC -XX:+UseContainerSupport -XX:MaxRAMPercentage=75"
ENTRYPOINT ["java","-jar","/app/ktor-docker-sample.jar"]