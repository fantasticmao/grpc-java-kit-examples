#!/usr/bin/env sh

java -javaagent:opentelemetry-javaagent.jar \
  -Dotel.service.name=helloworld_server \
  -Dotel.traces.exporter=logging \
  -Dotel.metrics.exporter=none \
  -jar ../helloworld-server/grpc/target/helloworld-server-grpc-1.0.0-SNAPSHOT.jar
