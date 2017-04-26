#!/bin/bash -eu

pushd app
./gradlew pactVerify
popd
