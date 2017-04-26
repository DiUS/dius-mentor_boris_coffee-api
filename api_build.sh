#!/bin/bash -eu

pushd app
./gradlew build --no-daemon
popd
