FROM mcr.microsoft.com/devcontainers/ruby:3.3
LABEL maintainer "mikoto2000 <mikoto2000@gmail.com>"

RUN curl -fsSL https://deb.nodesource.com/setup_22.x | bash - \
    && apt-get install -y nodejs

RUN gem install rails foreman

