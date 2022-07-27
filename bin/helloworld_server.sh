#!/usr/bin/env sh

SERVICE_NAME="helloworld_server"
OPEN_TELEMETRY_AGENT="opentelemetry-javaagent.jar"

# Enable JMX
JVM_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9999 \
-Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false \
-Djava.rmi.server.hostname=localhost"

# Enable OpenTelemetry
JVM_OPTS="${JVM_OPTS} -javaagent:${OPEN_TELEMETRY_AGENT} -Dotel.service.name=${SERVICE_NAME} \
-Dotel.traces.exporter=otlp \
-Dotel.exporter.otlp.traces.endpoint=http://localhost:4317 -Dotel.exporter.otlp.traces.protocol=grpc \
-Dotel.metrics.exporter=prometheus -Dotel.exporter.prometheus.port=9464 \
-Dotel.logs.exporter=none"

java ${JVM_OPTS} -jar helloworld-server-grpc-1.0.0-SNAPSHOT.jar
