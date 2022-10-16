package dev.mikoto2000.study.springboot.integration.tcp.serializer.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.springframework.integration.ip.tcp.serializer.AbstractByteArraySerializer;

/**
 * <code>
 * +--------------------------+---------+
 * | header(8Byte)            | content |
 * +-----------+--------------+---------+
 * | data type | content size | content |
 * | (4Byte)   | (4Byte)      |         |
 * +-----------+--------------+---------+
 * ※ ビッグエンディアン
 * </code>
 */
public class RequestSerializer extends AbstractByteArraySerializer {

    private final short HEADER_SIZE = 8;
    private final short CONTENT_SIZE_POSITION = 4;
    private final short CONTENT_SIZE_LENGTH = 4;

    @Override
    public byte[] deserialize(InputStream inputStream) throws IOException {
        System.out.println("start RequestSerializer deserialize");
        byte[] headerBuffer = readHeader(inputStream);
        System.out.println("RequestSerializer headerBuffer: " + Arrays.toString(headerBuffer));

        System.out.println(CONTENT_SIZE_POSITION);
        System.out.println(CONTENT_SIZE_POSITION + CONTENT_SIZE_LENGTH);
        byte[] contentSizeByteArray = Arrays.copyOfRange(headerBuffer, CONTENT_SIZE_POSITION, CONTENT_SIZE_POSITION + CONTENT_SIZE_LENGTH);
        System.out.println("RequestSerializer contentSizeByteArray: " + Arrays.toString(contentSizeByteArray));
        int contentSize = ByteBuffer.wrap(contentSizeByteArray).getInt();
        System.out.println("RequestSerializer contentSize: " + contentSize);
        byte[] contentBuffer = read(inputStream, contentSize);
        System.out.println("RequestSerializer contentBuffer: " + Arrays.toString(contentBuffer));

        ByteBuffer returnBuffer = ByteBuffer.allocate(HEADER_SIZE + contentSize);
        returnBuffer.put(headerBuffer);
        returnBuffer.put(contentBuffer);

        System.out.println("RequestSerializer returnBuffer: " + Arrays.toString(returnBuffer.array()));

        return returnBuffer.array();
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
        System.out.println("start RequestSerializer serialize");
        outputStream.write(bytes);
    }
}
