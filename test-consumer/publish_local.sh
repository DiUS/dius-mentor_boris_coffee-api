#!/bin/bash -eu

pushd ..

cp -f test-consumer/build/pacts/* app/build/pacts/

popd
