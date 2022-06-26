package com.lambda.mi.grpc.util;

import com.google.protobuf.ByteString;
import com.lambda.mi.grpc.generated.BytesMessage;
import io.grpc.stub.StreamObserver;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Iterator;

public class GrpcUtils {

    private static final int STREAM_CHUNK_SIZE = 64_000; // 64Kb

    private GrpcUtils() {
        //Util class
    }

    public static void writeObject(Serializable obj, StreamObserver<BytesMessage> streamObserver) {
        write(SerializationUtils.serialize(obj), streamObserver);
    }

    public static void writeFile(String filename, StreamObserver<BytesMessage> streamObserver) {
        try {
            byte[] bytes = GrpcUtils.class.getClassLoader().getResourceAsStream(filename).readAllBytes();
            write(bytes, streamObserver);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void write(byte[] bytes, StreamObserver<BytesMessage> streamObserver) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        while (byteBuffer.hasRemaining()) {
            int remaining = byteBuffer.remaining();
            int length = Math.min(remaining, STREAM_CHUNK_SIZE);
            BytesMessage request = BytesMessage.newBuilder().setChunk(ByteString.copyFrom(byteBuffer, length)).build();
            streamObserver.onNext(request);
        }
        streamObserver.onCompleted();
    }

    public static <T extends Serializable> T readObject(Iterator<BytesMessage> response, Class<T> clazz) {
        return SerializationUtils.deserialize(read(response), clazz);
    }

    public static String readFile(Iterator<BytesMessage> requestIterator) {
        String filename = "server_stored_file.csv";
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(read(requestIterator));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return filename;
    }

    private static byte[] read(Iterator<BytesMessage> response) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            while (response.hasNext()) {
                BytesMessage chunk = response.next();
                byte[] byteArray = chunk.getChunk().toByteArray();
                outputStream.write(byteArray);
            }
            return outputStream.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new byte[0];
    }
}
