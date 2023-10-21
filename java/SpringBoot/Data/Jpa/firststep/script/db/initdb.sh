#!/bin/bash

CURRENT_DIR=$(pwd)
SCRIPT_DIR=$(cd $(dirname ${BASH_SOURCE:-$0}); pwd)
PROJECT_ROOT=${SCRIPT_DIR}/../..

cd ${PROJECT_ROOT}
cat .devcontainer/initdb.d/*.sql | mysql -h mysql -u admin -p public
cd ${CURRENT_DIR}

