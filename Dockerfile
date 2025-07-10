# docker build -t graphql-spring-app .
# docker run -p 8090:8090 graphql-spring

FROM eclipse-temurin:21-jdk-alpine
RUN echo ${PWD} && ls -lR 
WORKDIR /app
COPY ./target/graphql-spring-0.0.1-SNAPSHOT.jar /app
CMD ["java", "-jar", "graphql-spring-0.0.1-SNAPSHOT.jar"]


##### Stage 1: Build the applicdation
#FROM openjdk:8-jdk-alpine as build
#
## Set the current working directory inside the image
#WORKDIR /app
#
#RUN echo ${PWD} && ls -lR
#
## Copy maven executable to the image
#COPY mvnw .
#COPY .mvn .mvn
#
## Copy the pom.xml file
#COPY pom.xml .
#
## Build all the dependencies in preparation to go offline. 
## This is a separate step so the dependencies will be cached unless 
## the pom.xml file has changed.
#RUN ./mvnw dependency:go-offline -B
#
## Copy the project source
#COPY src src
#
## Package the application
#RUN ./mvnw package -DskipTests
#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
#
##### Stage 2: A minimal docker image with command to run the app 
#FROM openjdk:8-jre-alpine
#
#ARG DEPENDENCY=/app/target/dependency
#
## Copy project dependencies from the build stage
#COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
#COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
#
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.user.credentail.UserApplication"]