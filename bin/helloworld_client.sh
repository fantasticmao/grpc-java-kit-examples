#!/usr/bin/env sh

NAME=$1
if [ -z "${NAME}" ]; then
  NAME='Guest'
fi

java -javaagent:opentelemetry-javaagent.jar \
  -Dotel.service.name=helloworld_client \
  -Dotel.traces.exporter=logging \
  -Dotel.metrics.exporter=none \
  -jar ../helloworld-client/target/helloworld-client-1.0.0-SNAPSHOT.jar "${NAME}"
