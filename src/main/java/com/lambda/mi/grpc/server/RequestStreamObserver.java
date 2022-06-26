package com.lambda.mi.grpc.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import com.lambda.mi.grpc.generated.BytesMessage;

import io.grpc.stub.StreamObserver;

public class RequestStreamObserver implements StreamObserver<BytesMessage> {
    private final List<BytesMessage> list = new ArrayList<>();

    private Consumer<Iterator<BytesMessage>> responseFunction = it -> {
        throw new UnsupportedOperationException("The response is not implemented yet.");
    };

    public RequestStreamObserver withResponse(Consumer<Iterator<BytesMessage>> responseFunction) {
        this.responseFunction = responseFunction;
        return this;
    }

    @Override
    public void onNext(BytesMessage request) {
        list.add(request);
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onCompleted() {
        responseFunction.accept(list.iterator());
    }
}
