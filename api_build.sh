#!/bin/bash -eu

docker run --rm \
  -e pactBrokerUsername=$pactBrokerUsername \
  -e pactBrokerPassword=$pactBrokerPassword \
  -e pactBrokerAccount=$pactBrokerAccount \
  -v "$PWD/app":/usr/src/provider \
  -w /usr/src/provider \
  openjdk:8 \
  ./gradlew build
