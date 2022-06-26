package com.lambda.mi.grpc.client;

import com.lambda.mi.grpc.generated.BytesMessage;
import com.lambda.mi.grpc.generated.LargeEntitiesServiceGrpc;
import com.lambda.mi.grpc.generated.LargeEntitiesServiceGrpc.LargeEntitiesServiceStub;
import com.lambda.mi.grpc.model.ReallyComplexObject;
import com.lambda.mi.grpc.util.GrpcUtils;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcClient {

    private final ManagedChannel channel;
    private final LargeEntitiesServiceStub asyncStub;

    public GrpcClient() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 8888)
                .usePlaintext()
                .build();
        asyncStub = LargeEntitiesServiceGrpc.newStub(channel);
    }

    public ReallyComplexObject sendData(ReallyComplexObject obj) {
        ResponseStreamObserver<BytesMessage> streamObserver = new ResponseStreamObserver<>();
        StreamObserver<BytesMessage> requestObserver = asyncStub.streamData(streamObserver);
        GrpcUtils.writeObject(obj, requestObserver);
        return GrpcUtils.readObject(streamObserver.getResponse(), ReallyComplexObject.class);
    }

    public String sendFile(String filename) {
        ResponseStreamObserver<BytesMessage> streamObserver = new ResponseStreamObserver<>();
        StreamObserver<BytesMessage> requestObserver = asyncStub.streamFile(streamObserver);
        GrpcUtils.writeFile(filename, requestObserver);
        return GrpcUtils.readObject(streamObserver.getResponse(), String.class);
    }

    public void shutdown() {
        channel.shutdown();
    }
}
