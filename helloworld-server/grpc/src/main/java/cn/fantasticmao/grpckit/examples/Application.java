package cn.fantasticmao.grpckit.examples;

import cn.fantasticmao.grpckit.springboot.factory.GrpcKitServerBuilderFactory;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

/**
 * Application
 *
 * @author fantasticmao
 * @since 2022-07-18
 */
@SpringBootApplication
public class Application {

    @Bean
    public GrpcKitServerBuilderFactory grpcKitServerBuilderFactory() {
        return (builder, services) -> builder
            .addServices(services)
            .addService(ProtoReflectionService.newInstance());
    }

    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(Application.class)
            .run(args);
        TimeUnit.HOURS.sleep(1);
    }
}
