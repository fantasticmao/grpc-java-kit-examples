#!/usr/bin/env sh

NAME=$1
if [ -z "${NAME}" ]; then
  NAME='Guest'
fi

java -javaagent:opentelemetry-javaagent.jar \
  -Dotel.service.name=helloworld_client \
  -Dotel.traces.exporter=otlp \
  -Dotel.exporter.otlp.traces.endpoint=http://localhost:4317 \
  -Dotel.exporter.otlp.traces.protocol=grpc \
  -Dotel.metrics.exporter=prometheus \
  -Dotel.exporter.prometheus.port=9465 \
  -Dotel.logs.exporter=none \
  -jar helloworld-client-1.0.0-SNAPSHOT.jar "${NAME}"
