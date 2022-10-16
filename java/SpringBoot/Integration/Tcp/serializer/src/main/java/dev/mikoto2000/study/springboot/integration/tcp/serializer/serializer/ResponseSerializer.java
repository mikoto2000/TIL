package dev.mikoto2000.study.springboot.integration.tcp.serializer.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.springframework.integration.ip.tcp.serializer.AbstractByteArraySerializer;

/**
 * +--------------------------+---------+
 * | header(4Byte)            | content |
 * +-----------+--------------+---------+
 * | data type | content size | content |
 * | (2Byte)   | (2Byte)      |         |
 * +-----------+--------------+---------+
 * ※ リトルエンディアン
 */
public class ResponseSerializer extends AbstractByteArraySerializer {

    private final short HEADER_SIZE = 4;
    private final short CONTENT_SIZE_POSITION = 2;
    private final short CONTENT_SIZE_LENGTH = 2;

    public byte[] deserialize(InputStream inputStream) throws IOException {
        System.out.println("start ResponseSerializer deserialize");
        byte[] headerBuffer = readHeader(inputStream);
        System.out.println("ResponseSerializer headerBuffer: " + Arrays.toString(headerBuffer));

        System.out.println(CONTENT_SIZE_POSITION);
        System.out.println(CONTENT_SIZE_POSITION + CONTENT_SIZE_LENGTH);
        byte[] contentSizeByteArray = Arrays.copyOfRange(headerBuffer, CONTENT_SIZE_POSITION, CONTENT_SIZE_POSITION + CONTENT_SIZE_LENGTH);
        System.out.println("ResponseSerializer contentSizeByteArray: " + Arrays.toString(contentSizeByteArray));
        short contentSize = ByteBuffer.wrap(contentSizeByteArray).order(ByteOrder.LITTLE_ENDIAN).getShort();
        System.out.println("ResponseSerializer contentSize: " + contentSize);
        byte[] contentBuffer = read(inputStream, contentSize);
        System.out.println("ResponseSerializer contentBuffer: " + Arrays.toString(contentBuffer));

        ByteBuffer returnBuffer = ByteBuffer.allocate(HEADER_SIZE + contentSize);
        returnBuffer.put(headerBuffer);
        returnBuffer.put(contentBuffer);

        var returnArray = returnBuffer.array();

        System.out.println("ResponseSerializer returnBuffer: " + Arrays.toString(returnArray));

        return returnArray;
    }

    private byte[] readHeader(InputStream is) throws IOException {
        return read(is, HEADER_SIZE);
    }

    private byte[] read(InputStream is, int size) throws IOException {
        byte[] buffer = new byte[size];
        var readedLength = 0;

        while (readedLength < size) {
            int len = is.read(buffer, readedLength, size - readedLength);
            if (len == 0) {
                throw new IOException("io error.");
            }
            readedLength += len;
        }
        return buffer;
    }

    @Override
    public void serialize(byte[] bytes, OutputStream outputStream) throws IOException {
        System.out.println("start ResponseSerializer serialize");
        outputStream.write(bytes);
    }
}