FROM ubuntu:14.04
maintainer Felipe Arruda "contato@arruda.blog.br"

RUN apt-get -qq update
RUN apt-get -qq -y upgrade

RUN apt-get install -y build-essential
RUN apt-get install -y python python-dev python-distribute python-pip

RUN apt-get install -y git

ADD ./scripts/ /scripts/
RUN chmod +x /scripts/start.sh

RUN mkdir /data

VOLUME ['/data','/project']

WORKDIR /project

ENV MANAGE_CMD runserver 0.0.0.0:8000
EXPOSE 8000
CMD /scripts/start.sh