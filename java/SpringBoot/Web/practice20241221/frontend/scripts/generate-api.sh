#!/bin/sh
openapi-generator-cli.sh generate -g typescript-axios -i http://localhost:8080/v3/api-docs -o src/api --additional-properties=useSingleRequestParameter=true,withInterfaces=true,supportsES6=true,sortModelPropertiesByRequiredFlag=true
