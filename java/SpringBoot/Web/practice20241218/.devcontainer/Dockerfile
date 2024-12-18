FROM mcr.microsoft.com/devcontainers/java:1-21-bookworm

RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive \
        apt-get install -y \
            postgresql-client \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN curl -LO https://nodejs.org/dist/v22.12.0/node-v22.12.0-linux-x64.tar.xz \
    && tar xfv node-v22.12.0-linux-x64.tar.xz -C /opt \
    && rm node-v22.12.0-linux-x64.tar.xz

ENV PATH=$PATH:/opt/node-v22.12.0-linux-x64/bin
