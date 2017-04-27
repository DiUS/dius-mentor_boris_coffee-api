#!/bin/bash -eu

pushd app
./gradlew pactVerify --no-daemon -Dpact.colors.enabled=false
popd
