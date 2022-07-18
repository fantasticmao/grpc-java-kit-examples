package cn.fantasticmao.grpckit.examples;

import cn.fantasticmao.grpckit.examples.helloworld.GreeterGrpc;
import cn.fantasticmao.grpckit.examples.helloworld.HelloReply;
import cn.fantasticmao.grpckit.examples.helloworld.HelloRequest;
import cn.fantasticmao.grpckit.springboot.annotation.GrpcService;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GreeterService
 *
 * @author fantasticmao
 * @since 2022-07-18
 */
@GrpcService
public class GreeterService extends GreeterGrpc.GreeterImplBase {
    private static Logger LOGGER = LoggerFactory.getLogger(GreeterService.class);

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        LOGGER.info("receive a new message, name: {}", req.getName());
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
