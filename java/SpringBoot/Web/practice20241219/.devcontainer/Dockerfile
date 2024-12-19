FROM mcr.microsoft.com/devcontainers/java:1-21-bookworm

RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive \
        apt-get install -y \
            postgresql-client \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Node.js
RUN curl -LO https://nodejs.org/dist/v22.12.0/node-v22.12.0-linux-x64.tar.xz \
    && tar xfv node-v22.12.0-linux-x64.tar.xz -C /opt \
    && rm node-v22.12.0-linux-x64.tar.xz

ENV PATH=$PATH:/opt/node-v22.12.0-linux-x64/bin


# OpenAPI Generator
RUN curl -LO https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/7.10.0/openapi-generator-cli-7.10.0.jar \
    && mv openapi-generator-cli-7.10.0.jar /opt
COPY ./openapi-generator-cli.sh /opt/openapi-generator-cli/bin/openapi-generator-cli.sh
RUN chmod +x /opt/openapi-generator-cli/bin/openapi-generator-cli.sh

ENV PATH=$PATH:/opt/openapi-generator-cli/bin

