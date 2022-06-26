package com.lambda.mi.grpc.util;

import java.io.*;

public class SerializationUtils {

    private SerializationUtils() {
        // util class
    }

    public static byte[] serialize(Serializable obj) {
        byte[] bytes = new byte[0];

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {

            out.writeObject(obj);
            out.flush();
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public static <T> T deserialize(byte[] poBytes, Class<T> clazz) {
        Object o = null;

        try (ByteArrayInputStream bis = new ByteArrayInputStream(poBytes);
             ObjectInput in = new ObjectInputStream(bis)) {
            o = in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        if (clazz.isInstance(o)) {
            return clazz.cast(o);
        } else {
            System.err.println("Object is not of class " + clazz);
            return null;
        }
    }
}
