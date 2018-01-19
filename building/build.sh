#!/bin/bash

if [ $TRAVIS_PULL_REQUEST = "false" ] && [ $TRAVIS_BRANCH = "master" ]; then
  ./gradlew clean build dokka

  # Upload docs
  cd ..
  git clone --branch=master "https://SUPERCILEX:${GIT_LOGIN}@github.com/sertain/javadocs.git" javadocs &> /dev/null
  git config --global user.email "saveau.alexandre@gmail.com"
  git config --global user.name "Alex Saveau"

  cp sertain/core/build/javadocs/** -r javadocs
  cd javadocs

  git add .
  git commit -a -m "Update docs from https://github.com/sertain/sertain/compare/${TRAVIS_COMMIT_RANGE}"
  git push -u origin master &> /dev/null
else
  ./gradlew clean assemble check
fi
