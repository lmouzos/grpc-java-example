package com.lambda.mi.grpc;

import com.lambda.mi.grpc.model.ReallyComplexObject;
import com.lambda.mi.grpc.client.GrpcClient;
import com.lambda.mi.grpc.server.GrpcServer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        GrpcServer server = new GrpcServer();
        GrpcClient client = new GrpcClient();

        server.start();

        List<String> strings = Arrays.asList("el1", "el2", "el3", "el4", "el5");
        ReallyComplexObject data = new ReallyComplexObject(
                "1234-5678-9012-345",
                1,
                strings,
                new HashSet<>(strings)
        );
        ReallyComplexObject responseObject = client.sendData(data);
        System.out.println("Are the data echoed back from the server? " + responseObject.equals(data));

        String response = client.sendFile("file.csv");
        System.out.println("Did the server get the file sent by the client? " + response);

        client.shutdown();
        server.shutdown();
    }
}
