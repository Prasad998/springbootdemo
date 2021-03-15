# Start with a base image containing Java runtime
FROM adoptopenjdk/openjdk11:ubi-minimal-jre

#ENV VERSION DEMO_VERSION

ENV VERSION "0.0.1-SNAPSHOT"

# Add Maintainer Info
LABEL author.name="Manoj Dhanorkar " \
      author.email="manoj.dhanorkar@psl.com" \
      version="$VERSION" \
      vendor="psl" \
      description="Demo Server"

# Set HOME_DIR environment
ENV DEMOAPP_HOME /opt/product/demo-server

#Create  home directory
RUN mkdir -p $DEMOAPP_HOME/properties

# Make port 8080 available to the world outside this container
EXPOSE 8080

#Add jars to  home directory
COPY ./target/demo-$VERSION.jar $DEMOAPP_HOME
COPY ./init_container.sh $DEMOAPP_HOME
COPY ./src/main/resources/application.properties $DEMOAPP_HOME/properties
COPY ./src/main/resources/application-dev.properties $DEMOAPP_HOME/properties
COPY ./src/main/resources/application-prod.properties $DEMOAPP_HOME/properties

#Default working directory
WORKDIR $DEMOAPP_HOME

#Verify the files
RUN pwd && \
ls -ltr

RUN chmod +x $DEMOAPP_HOME/init_container.sh

CMD ["sh","/opt/product/demo-server/init_container.sh"]
