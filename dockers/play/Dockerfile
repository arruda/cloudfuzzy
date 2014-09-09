FROM ubuntu:precise
MAINTAINER Felipe Arruda Pontes <contato@arruda.blog.br>

ENV http_proxy http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128
ENV https_proxy http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128

RUN echo 'Acquire::http::Proxy "http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128";' >> "/etc/apt/apt.conf.d/01proxy"
RUN echo 'Acquire::https::Proxy "http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128";' >> "/etc/apt/apt.conf.d/01proxy"

# make sure the package repository is up to date
RUN apt-get update && apt-get -y install python-software-properties

RUN add-apt-repository ppa:webupd8team/java
RUN apt-get update && apt-get -y upgrade

# automatically accept oracle license
RUN echo oracle-java6-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
# and install java 6 oracle jdk

RUN apt-get -y install oracle-java6-installer && apt-get clean
RUN update-alternatives --display java
RUN echo "JAVA_HOME=/usr/lib/jvm/java-6-oracle" >> /etc/environment

ENV JAVA_HOME /usr/lib/jvm/java-6-oracle
RUN ln -s /usr/lib/jvm/java-6-oracle/ /usr/lib/jvm/default-java

# Get play

RUN wget http://downloads.typesafe.com/releases/play-2.0.2.zip -O play-2.0.2.zip

RUN apt-get install zip unzip
RUN unzip /play-2.0.2.zip -d /usr/share

ADD ./conf/build /usr/share/play-2.0.2/framework/build
RUN chmod +x /usr/share/play-2.0.2/framework/build
RUN chmod +x /usr/share/play-2.0.2/play

RUN ln -s /usr/share/play-2.0.2/play /usr/bin/play

# prepare cloudfuzzy
# RUN mkdir /project

# Add project volume
VOLUME  ["/project"]

# Add CF env vars
ENV CF_XFL_PATH /project/extraFiles/xfls/
ENV CF_PKG_PATH /project/extraFiles/

WORKDIR /project/cloudfuzzy

EXPOSE 9000

CMD /usr/bin/play "start -Dhttp.port=9000 -DapplyEvolutions.default=true"

