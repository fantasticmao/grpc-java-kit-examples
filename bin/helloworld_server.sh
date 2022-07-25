#!/usr/bin/env sh

java -javaagent:opentelemetry-javaagent.jar \
  -Dotel.service.name=helloworld_server \
  -Dotel.traces.exporter=otlp \
  -Dotel.exporter.otlp.traces.endpoint=http://localhost:4317 \
  -Dotel.exporter.otlp.traces.protocol=grpc \
  -Dotel.metrics.exporter=prometheus \
  -Dotel.exporter.prometheus.port=9464 \
  -Dotel.logs.exporter=none \
  -jar helloworld-server-grpc-1.0.0-SNAPSHOT.jar
