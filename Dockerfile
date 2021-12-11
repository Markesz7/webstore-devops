FROM openjdk:11-jre-slim
ENV APP_PATH /app
RUN mkdir ${APP_PATH}
WORKDIR ${APP_PATH}
COPY target/*.jar ${APP_PATH}/run.jar
CMD java -Dserver.port=$PORT $JAVA_OPTS -jar run.jar