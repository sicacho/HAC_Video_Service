FROM java:8

MAINTAINER trustme013@gmail.com

VOLUME /logs

EXPOSE 8090

ENV USER_NAME HACService

ENV APP_HOME /home/$USER_NAME/app

RUN useradd -ms /bin/bash $USER_NAME

RUN mkdir $APP_HOME

RUN mkdir $APP_HOME/ffmpeg

RUN mkdir $APP_HOME/movie

RUN mkdir $APP_HOME/movieEncoded

ADD encoder/target/encoder-0.0.1-SNAPSHOT.jar $APP_HOME/encoder-0.0.1-SNAPSHOT.jar

ADD encoder/ffmpeg $APP_HOME/ffmpeg

RUN chown $USER_NAME $APP_HOME/encoder-0.0.1-SNAPSHOT.jar

RUN chmod 755 -R $APP_HOME/movieEncoded

USER $USER_NAME

WORKDIR $APP_HOME

VOLUME $APP_HOME/movie

VOLUME $APP_HOME/movieEncoded

RUN bash -c 'touch encoder-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=Docker","-jar","encoder-0.0.1-SNAPSHOT.jar"]
