package com.lambda.mi.grpc.client;

import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResponseStreamObserver<T> implements StreamObserver<T> {
    private final List<T> list = new ArrayList<>();

    private volatile boolean isCompleted = false;

    @Override
    public void onCompleted() {
        isCompleted = true;
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onNext(T response) {
        this.list.add(response);
    }

    public Iterator<T> getResponse() {
        // wait until the request stream is done
        while (!isCompleted) {
            Thread.onSpinWait();
        }
        return list.iterator();
    }
}
