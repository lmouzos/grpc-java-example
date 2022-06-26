package com.lambda.mi.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GrpcServer {

    private final Server server;

    public GrpcServer() {
        LargeEntitiesServiceImpl service = new LargeEntitiesServiceImpl();
        this.server = ServerBuilder.forPort(8888)
                .addService(service)
                .build();
    }

    public void start(){
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            server.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
