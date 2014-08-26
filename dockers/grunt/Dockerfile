FROM ubuntu:14.04
maintainer Felipe Arruda "contato@arruda.blog.br"

ENV http_proxy http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128
ENV https_proxy http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128

RUN echo 'Acquire::http::Proxy "http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128";' >> "/etc/apt/apt.conf.d/01proxy"
RUN echo 'Acquire::https::Proxy "http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128";' >> "/etc/apt/apt.conf.d/01proxy"

RUN apt-get -qqy update

RUN apt-get -qqy install git npm

RUN ln -s /usr/bin/nodejs /usr/bin/node

RUN useradd -m -s /bin/bash -d /home/grunt grunt
RUN adduser grunt sudo
USER grunt
ENV HOME /home/grunt


ADD scripts /scripts

VOLUME  ["/project"]

WORKDIR /project


USER root

CMD /scripts/start.sh