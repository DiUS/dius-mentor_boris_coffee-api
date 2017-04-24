#!/bin/bash -eu

docker run --rm \
  -v "$PWD/app":/usr/src/provider \
  -w /usr/src/provider \
  openjdk:8 \
  ./gradlew build
