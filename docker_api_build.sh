#!/bin/bash -eu

mnt_dir='/workspace'

docker run \
  -v `pwd`:$mnt_dir -w $mnt_dir \
  openjdk:8 \
  ./api_build.sh
