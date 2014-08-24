FROM ubuntu:14.04

RUN apt-get update -q
RUN apt-get install -qy curl

# Install rvm, ruby, bundler
RUN curl -sSL https://get.rvm.io | bash -s stable
RUN /bin/bash -l -c "rvm requirements"
RUN /bin/bash -l -c "rvm install 2.1.0"
RUN /bin/bash -l -c "gem install bundler --no-ri --no-rdoc"
#RUN echo "source /usr/local/rvm/scripts/rvm" >> /root/.bashrc

ADD ./Gemfile /Gemfile

RUN /bin/bash -l -c "bundle install"


VOLUME  ["/project"]

WORKDIR /project

CMD /bin/bash -l -c "compass watch"