package cn.fantasticmao.grpckit.examples;

import cn.fantasticmao.grpckit.examples.helloworld.GreeterGrpc;
import cn.fantasticmao.grpckit.examples.helloworld.HelloReply;
import cn.fantasticmao.grpckit.examples.helloworld.HelloRequest;
import cn.fantasticmao.grpckit.springboot.annotation.GrpcClient;
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
    @GrpcClient
    private GreeterGrpc.GreeterBlockingStub stub;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Client.class, args);

        Client client = applicationContext.getBean(Client.class);
        HelloRequest request = HelloRequest.newBuilder()
            .setName("Sam")
            .build();
        HelloReply reply = client.stub.sayHello(request);
        System.out.println(reply.getMessage());
    }
}
