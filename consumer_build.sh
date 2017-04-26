#!/bin/bash -eu

pushd test-consumer
./gradlew build pactPublish
popd
