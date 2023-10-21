#!/bin/sh

CURRENT_DIR=$(pwd)
SCRIPT_DIR=$(cd $(dirname ${BASH_SOURCE:-$0}); pwd)
PROJECT_ROOT=${SCRIPT_DIR}/../..

cd ${PROJECT_ROOT}
./mvnw org.hibernate.tool:hibernate-tools-maven:hbm2java
cd ${CURRENT_DIR}

