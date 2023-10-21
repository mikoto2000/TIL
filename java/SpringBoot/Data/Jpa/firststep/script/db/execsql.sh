#!/bin/bash

cat $* | mysql -h mysql -u admin -p public

