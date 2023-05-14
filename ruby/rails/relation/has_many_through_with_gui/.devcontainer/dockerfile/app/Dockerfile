FROM ubuntu:22.04 AS production
LABEL maintainer "mikoto2000 <mikoto2000@gmail.com>"

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update \
    && apt-get install -y \
        ruby-full curl build-essential libpq-dev tzdata \
    && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs

RUN gem install bundler rails \
    && npm i -g yarn


FROM production AS development
LABEL maintainer "mikoto2000 <mikoto2000@gmail.com>"

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update \
    && apt-get install -y \
        vim git
