package com.lambda.mi.grpc.server;

import com.lambda.mi.grpc.generated.BytesMessage;
import com.lambda.mi.grpc.model.ReallyComplexObject;
import com.lambda.mi.grpc.util.GrpcUtils;
import io.grpc.stub.StreamObserver;
import com.lambda.mi.grpc.generated.LargeEntitiesServiceGrpc;

public class LargeEntitiesServiceImpl extends LargeEntitiesServiceGrpc.LargeEntitiesServiceImplBase {

    @Override
    public StreamObserver<BytesMessage> streamData(StreamObserver<BytesMessage> responseObserver) {
        return new RequestStreamObserver()
                .withResponse(requestIterator -> {
                    ReallyComplexObject requestObj = GrpcUtils.readObject(requestIterator, ReallyComplexObject.class);
                    GrpcUtils.writeObject(requestObj, responseObserver);
                });
    }

    @Override
    public StreamObserver<BytesMessage> streamFile(StreamObserver<BytesMessage> responseObserver) {
        return new RequestStreamObserver()
                .withResponse(requestIterator -> {
                    String file = GrpcUtils.readFile(requestIterator);
                    GrpcUtils.writeObject(file, responseObserver);
                });
    }
}
