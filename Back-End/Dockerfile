#### Stage 1: Build the application
FROM openjdk:11.0.6-jre-slim AS build

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .


#RUN dos2unix mvnw

RUN ./mvnw dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application without downloading dependacies
RUN ./mvnw clean package -DskipTests
# build the app (no dependency download here)
#RUN --mount=type=cache,target=/root/.m2  ./mvnw clean package -Dmaven.test.skip

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#### Stage 2: A minimal docker image with command to run the app
FROM openjdk:11-jre-slim

ARG DEPENDENCY=/app/target/dependency

#Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.matome.Application"]