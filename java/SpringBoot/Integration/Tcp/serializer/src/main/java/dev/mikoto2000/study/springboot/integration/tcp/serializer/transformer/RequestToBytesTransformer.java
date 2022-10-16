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
public class RequestToBytesTransformer implements GenericTransformer<Request, byte[]> {

    private final short HEADER_SIZE = 8;

    @Override
    public byte[] transform(Request input) {
        System.out.println("RequestToBytesTransformer input: " + input);

        ByteBuffer bf = ByteBuffer.allocate(HEADER_SIZE + input.getPayload().length);

        // タイプ設定
        bf.putInt(input.getType());

        // ペイロードサイズ設定
        bf.putInt(input.getPayload().length);

        // コンテンツセット
        bf.put(input.getPayload());

        var bytes = bf.array();

        System.out.println("RequestToBytesTransformer output: " + Arrays.toString(bytes));

        return bytes;
    }
}
