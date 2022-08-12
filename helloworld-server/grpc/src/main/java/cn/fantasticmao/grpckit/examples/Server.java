package cn.fantasticmao.grpckit.examples;

import cn.fantasticmao.grpckit.boot.factory.GrpcKitServerBuilderFactory;
import cn.fantasticmao.grpckit.boot.factory.GrpcKitThreadFactory;
import cn.fantasticmao.grpckit.examples.helloworld.GreeterGrpc;
import cn.fantasticmao.grpckit.examples.helloworld.HelloReply;
import cn.fantasticmao.grpckit.examples.helloworld.HelloRequest;
import cn.fantasticmao.grpckit.springboot.annotation.GrpcService;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Server
 * <p>
 * VM Options: <pre>
 * -javaagent:${OPEN_TELEMETRY_AGENT}
 * -Dotel.service.name=helloworld_server
 * -Dotel.traces.exporter=otlp
 * -Dotel.exporter.otlp.traces.endpoint=http://localhost:4317
 * -Dotel.exporter.otlp.traces.protocol=grpc
 * -Dotel.metrics.exporter=prometheus
 * -Dotel.exporter.prometheus.port=9464
 * -Dotel.instrumentation.micrometer.prometheus-mode.enabled=true
 * -Dotel.logs.exporter=none
 * </pre>
 *
 * @author fantasticmao
 * @since 2022-07-18
 */
@SpringBootApplication
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @Bean
    public GrpcKitServerBuilderFactory grpcKitServerBuilderFactory() {
        ExecutorService executor = new ThreadPoolExecutor(10, 200,
            10, TimeUnit.MINUTES, new SynchronousQueue<>(),
            new GrpcKitThreadFactory.Server("helloworld_server"));
        return builder -> GrpcKitServerBuilderFactory.Default.INSTANCE.customize(builder)
            .executor(executor);
    }

    @GrpcService
    public static class GreeterService extends GreeterGrpc.GreeterImplBase {
        private static final Logger LOGGER = LoggerFactory.getLogger(GreeterService.class);

        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            LOGGER.info("Server receive a new message, name: {}", req.getName());
            HelloReply reply = HelloReply.newBuilder()
                .setMessage("Hello " + req.getName())
                .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
