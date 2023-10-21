FROM mcr.microsoft.com/devcontainers/java:17

USER vscode

RUN apt-get update \
    && apt-get install \
        default-mysql-client \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir /home/vscode/.m2

