package cn.fantasticmao.grpckit.examples;

import cn.fantasticmao.grpckit.boot.factory.GrpcKitChannelBuilderFactory;
import cn.fantasticmao.grpckit.boot.factory.GrpcKitThreadFactory;
import cn.fantasticmao.grpckit.examples.helloworld.GreeterGrpc;
import cn.fantasticmao.grpckit.examples.helloworld.HelloReply;
import cn.fantasticmao.grpckit.examples.helloworld.HelloRequest;
import cn.fantasticmao.grpckit.springboot.annotation.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Client
 * <p>
 * VM Options: <pre>
 * -javaagent:${OPEN_TELEMETRY_AGENT}
 * -Dotel.service.name=helloworld_client
 * -Dotel.traces.exporter=otlp
 * -Dotel.exporter.otlp.traces.endpoint=http://localhost:4317
 * -Dotel.exporter.otlp.traces.protocol=grpc
 * -Dotel.metrics.exporter=logging
 * -Dotel.logs.exporter=none
 * </pre>
 *
 * @author fantasticmao
 * @since 2022-07-21
 */
@SpringBootApplication
public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    @GrpcClient
    private GreeterGrpc.GreeterBlockingStub stub;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Client.class, args);

        Client client = applicationContext.getBean(Client.class);
        HelloRequest request = HelloRequest.newBuilder()
            .setName(args.length != 0 ? args[0] : "Guest")
            .build();
        HelloReply reply = client.stub.sayHello(request);
        LOGGER.info("Client receive a new message: {}", reply.getMessage());
    }

    @Bean
    public GrpcKitChannelBuilderFactory grpcKitChannelBuilderFactory() {
        ExecutorService executor = new ThreadPoolExecutor(5, 50,
            10, TimeUnit.MINUTES, new ArrayBlockingQueue<>(200),
            new GrpcKitThreadFactory.Channel("helloworld_client"));
        return builder -> GrpcKitChannelBuilderFactory.Default.INSTANCE.customize(builder)
            .executor(executor);
    }
}
