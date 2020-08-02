#!/bin/bash

# $1 is the build target. One of Dev, Nightly, QA, Alpha, Beta, ??Production??
set -ex

# Fix error in Gradle 2.14.1 "$TERM not set"
export GRADLE_OPTS=-Dorg.gradle.native=false

# Fix "daemon disappeared" error
GRADLE_OPTS="$GRADLE_OPTS -Dorg.gradle.daemon=true -Dorg.gradle.jvmargs=-Xmx2048m"

# Get Build target
if [ -n "$1" ]
then
    TARGET=$1
elif [ -n "$ARG_STAGE" ]
then
    TARGET=$ARG_STAGE
else
    echo "Build target is not specified. Use parameter or set ARG_STAGE environment variable."
    exit 1;
fi

OUTDIR="../pocketWeatherArch-out" # This should be a parameter

# Debugging info
date
whoami
env
pwd
ls -al

# Git info
git_repo=$(basename `git rev-parse --show-toplevel`)
git_hash=$(git rev-parse HEAD)

# Load version code from 'version' branch
source ../version-info/version_code

cat app/version.properties
 
# Update Gradle's version code
sed -i "/^[[:space:]]*VERSION_CODE=/ s/=.*/=$VERSION_CODE/" app/version.properties

cat app/version.properties

# Load version name updated properties file. Includes the updated version code.
source app/version.properties
  gradle test${TARGET}ReleaseUnitTest --debug
  gradle assemble${TARGET}Release
  sleep 10
  gradle appDistributionUpload${TARGET}Release

# Store build artifacts for later steps
if [ -d "$OUTDIR" ]; then
    # The app binary
    cp app/build/outputs/apk/${TARGET,,}/release/app-${TARGET,,}-release.apk $OUTDIR/pocketWeatherArch_${TARGET,,}_${VERSION_CODE}.apk

    # Release info for Github releases
    echo "pocketWeatherArch $TARGET ($VERSION_CODE)" > $OUTDIR/release-name
    echo "pocketWeatherArch${TARGET}_${VERSION_CODE}" > $OUTDIR/release-tag
    echo "Built from https://github.com/kobusPitzer/${git_repo}/commit/${git_hash}" > $OUTDIR/release-body
    echo "$(date '+%Y-%m-%d %H:%M')
Pipeline: $TARGET
Build: $VERSION_CODE" > $OUTDIR/release-commit

    # Release tag in main source repo
    echo "$VERSION_NAME" > $OUTDIR/main-repo-tag
    echo "Build number: $VERSION_CODE" > $OUTDIR/main-repo-annotation

    # Extra info
    echo "$VERSION_NAME" > $OUTDIR/release-version-string
    echo "${git_repo}" > $OUTDIR/src_git_repo
    echo "${git_hash}" > $OUTDIR/src_git_hash
    echo "${VERSION_CODE}" > $OUTDIR/src_version_code

else
    echo Directory $OUTDIR does not exist
    echo Not publishing APK to Github Releases
    ls .. -ls
fi
