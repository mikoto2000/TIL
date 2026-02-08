FROM mcr.microsoft.com/devcontainers/java:1-21-bookworm
LABEL maintainer "mikoto2000 <mikoto2000@gmail.com>"

RUN curl -fsSL https://dl.yarnpkg.com/debian/pubkey.gpg \
  | gpg --dearmor > /usr/share/keyrings/yarn-archive-keyring.gpg \
  && chmod a+r /usr/share/keyrings/yarn-archive-keyring.gpg

