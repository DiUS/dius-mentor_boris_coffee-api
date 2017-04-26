#!/bin/bash -eu

pushd test-consumer
./gradlew build pactPublish --no-daemon
popd
