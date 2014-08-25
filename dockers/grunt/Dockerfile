FROM ubuntu:14.04
maintainer Felipe Arruda "contato@arruda.blog.br"

RUN apt-get -qqy update

RUN useradd -m -s /bin/bash -d /home/grunt grunt
RUN sudo adduser grunt sudo
USER grunt
ENV HOME /home/grunt

RUN sudo apt-get -qqy install git npm



ADD scripts /scripts

VOLUME  ["/home/grunt/project"]

WORKDIR /home/grunt/project



CMD /scripts/start.sh