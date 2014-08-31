FROM ubuntu:14.04
maintainer Felipe Arruda "contato@arruda.blog.br"

RUN apt-get -qq update

RUN apt-get install -qq -y build-essential
RUN apt-get install -y python python-dev python-distribute python-pip


ADD ./scripts/ /scripts/

VOLUME ['/data','/project']

WORKDIR /project

CMD /scripts/start.sh