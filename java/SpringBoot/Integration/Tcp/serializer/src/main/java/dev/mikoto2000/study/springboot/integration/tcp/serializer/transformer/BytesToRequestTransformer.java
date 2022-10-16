package dev.mikoto2000.study.springboot.integration.tcp.serializer.transformer;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

import dev.mikoto2000.study.springboot.integration.tcp.serializer.model.Request;

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
@Component
public class BytesToRequestTransformer implements GenericTransformer<byte[], Request> {

    @Override
    public Request transform(byte[] input) {
        System.out.println("RequestTransformer input: " + Arrays.toString(input));

        var bb = ByteBuffer.wrap(input);

        var type = bb.getInt();
        var length = bb.getInt();
        var payload = new byte[length];
        bb.get(payload);

        var request =new Request(type, payload);

        System.out.println("RequestTransformer output: " + request);

        return request;
    }
}
