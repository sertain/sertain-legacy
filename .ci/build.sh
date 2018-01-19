#!/bin/bash

if [ $TRAVIS_PULL_REQUEST = "false" ] && [ $TRAVIS_BRANCH = "master" ]; then
  ./gradlew clean build
else
  ./gradlew clean assemble check
fi
