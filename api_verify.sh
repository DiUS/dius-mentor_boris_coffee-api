#!/bin/bash -eu

pushd app
./gradlew pactVerify --no-daemon
popd
