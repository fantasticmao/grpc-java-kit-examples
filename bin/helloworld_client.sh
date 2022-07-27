#!/usr/bin/env sh

NAME=$1
if [ -z "${NAME}" ]; then
  NAME='Guest'
fi

SERVICE_NAME="helloworld_client"
OPEN_TELEMETRY_AGENT="opentelemetry-javaagent.jar"

# Enable OpenTelemetry
JVM_OPTS="${JVM_OPTS} -javaagent:${OPEN_TELEMETRY_AGENT} -Dotel.service.name=${SERVICE_NAME} \
-Dotel.traces.exporter=otlp \
-Dotel.exporter.otlp.traces.endpoint=http://localhost:4317 -Dotel.exporter.otlp.traces.protocol=grpc \
-Dotel.metrics.exporter=none \
-Dotel.logs.exporter=none"

java ${JVM_OPTS} -jar helloworld-client-1.0.0-SNAPSHOT.jar "${NAME}"
