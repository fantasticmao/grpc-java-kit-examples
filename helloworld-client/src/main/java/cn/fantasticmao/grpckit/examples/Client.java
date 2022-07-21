package cn.fantasticmao.grpckit.examples;

import cn.fantasticmao.grpckit.examples.helloworld.GreeterGrpc;
import cn.fantasticmao.grpckit.examples.helloworld.HelloReply;
import cn.fantasticmao.grpckit.examples.helloworld.HelloRequest;
import cn.fantasticmao.grpckit.springboot.annotation.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Client
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
}
