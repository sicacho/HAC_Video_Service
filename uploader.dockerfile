FROM java:8

MAINTAINER trustme013@gmail.com

VOLUME /logs

EXPOSE 8090

ENV USER_NAME HACService

ENV APP_HOME /home/$USER_NAME/app

RUN useradd -ms /bin/bash $USER_NAME

RUN mkdir $APP_HOME

RUN mkdir $APP_HOME/movie

ADD uploader/target/uploader-0.0.1-SNAPSHOT.jar $APP_HOME/uploader-0.0.1-SNAPSHOT.jar

RUN chown $USER_NAME $APP_HOME/uploader-0.0.1-SNAPSHOT.jar

USER $USER_NAME

WORKDIR $APP_HOME

VOLUME $APP_HOME/movie

RUN bash -c 'touch uploader-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=Docker","-jar","uploader-0.0.1-SNAPSHOT.jar"]
