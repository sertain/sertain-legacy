#!/bin/bash
set -eo pipefail # Exit with nonzero exit code if anything fails

# Pull requests and commits to other branches shouldn't try to deploy, just build to verify
if [ "$TRAVIS_PULL_REQUEST" != "false" -o "$TRAVIS_BRANCH" != "doc-deploys" ]; then
    echo "Skipping deploy."
    exit 0
fi

./gradlew dokka

# Upload docs
cd ..
git clone --branch=master "https://SUPERCILEX:${GIT_LOGIN}@github.com/sertain/javadocs-tests.git" javadocs &> /dev/null

git config --global user.name "Travis CI"
git config --global user.email "social@sert2521.org"

cp -r sertain/core/build/javadocs/** javadocs
cd javadocs

# If there are no real changes to the compiled out (e.g. this is a README update), then just bail.
if git diff --quiet; then
    echo "No changes to the output on this push; exiting."
    exit 0
fi

# If the only changes are timestamp updates, then bail as well. This can be determined by checking
# if the number of additions, deletions, and changed files all match up.
FILES=$(git log -1 --shortstat | awk '/^ [0-9]/ { f += $1; i += $4; d += $6 } END { printf(f) }')
INSERT=$(git log -1 --shortstat | awk '/^ [0-9]/ { f += $1; i += $4; d += $6 } END { printf(i) }')
DELETE=$(git log -1 --shortstat | awk '/^ [0-9]/ { f += $1; i += $4; d += $6 } END { printf(d) }')

if [ "$FILES" = "$INSERT" ] && [ "$INSERT" = "$DELETE" ]; then
   echo "Only timestamp updates; exiting."
   exit 0
fi

git add .
git commit -m "Update docs from https://github.com/sertain/sertain/compare/${TRAVIS_COMMIT_RANGE}"
git push -u origin master &> /dev/null
