#!/bin/bash -eu

mnt_dir='/workspace'

docker run \
  -v `pwd`:$mnt_dir -w $mnt_dir \
  -e pactBrokerAccount=$pactBrokerAccount \
  -e pactBrokerUsername=$pactBrokerUsername \
  -e pactBrokerPassword=$pactBrokerPassword \
  -t \
  openjdk:8 \
  ./api_verify.sh
