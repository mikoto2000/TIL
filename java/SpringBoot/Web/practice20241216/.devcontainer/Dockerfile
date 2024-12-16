FROM mcr.microsoft.com/devcontainers/java:1-21-bookworm

RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y postgresql-client \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*
