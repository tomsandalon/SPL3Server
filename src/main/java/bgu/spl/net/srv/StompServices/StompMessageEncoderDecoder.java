package bgu.spl.net.srv.StompServices;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.io.*;
import java.nio.ByteBuffer;

public class StompMessageEncoderDecoder implements MessageEncoderDecoder<String> {

    private final int BUFFER_SIZE = 4;
    private final ByteBuffer lengthBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    private byte[] objectBytes = null;
    private int objectBytesIndex = 0;

    @Override
    public String decodeNextByte(byte nextByte) {
        if (objectBytes == null) {
            lengthBuffer.put(nextByte);
            if (!lengthBuffer.hasRemaining()) {
                lengthBuffer.flip();
                objectBytes = new byte[lengthBuffer.getInt()];
                objectBytesIndex = 0;
                lengthBuffer.clear();
            }
        } else {
            objectBytes[objectBytesIndex] = nextByte;
            if (++objectBytesIndex == objectBytes.length) {
                String result = deserializeObject();
                objectBytes = null;
                return result;
            }
        }
        return null;
    }

    @Override
    public byte[] encode(String message) {
        return serializeObject(message);
    }

    private String deserializeObject() {
        try {
            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(objectBytes));
            return (String) in.readObject();
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot deserialize this object", e);
        }
    }

    private byte[] serializeObject(String message) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            for (int i = 0; i < BUFFER_SIZE; i++) {
                bytes.write(0);
            }

            ObjectOutput out = new ObjectOutputStream(bytes);
            out.writeObject(message);
            out.flush();
            byte[] result = bytes.toByteArray();

            //now write the object size
            ByteBuffer.wrap(result).putInt(result.length - BUFFER_SIZE);
            return result;

        } catch (Exception e) {
            throw new IllegalArgumentException("cannot serialize object", e);
        }
    }
}
