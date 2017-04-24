#!/bin/bash -eu

docker run --rm \
  -e pactBrokerUsername=$pactBrokerUsername \
  -e pactBrokerPassword=$pactBrokerPassword \
  -e pactBrokerAccount=$pactBrokerAccount \
  -v "$PWD/test-consumer":/usr/src/consumer \
  -w /usr/src/consumer \
  openjdk:8 \
  ./gradlew build pactPublish
