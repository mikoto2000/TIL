package dev.mikoto2000.study.springboot.integration.tcp.serializer.transformer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

import dev.mikoto2000.study.springboot.integration.tcp.serializer.model.Response;

/**
 * +--------------------------+---------+
 * | header(4Byte)            | content |
 * +-----------+--------------+---------+
 * | data type | content size | content |
 * | (2Byte)   | (2Byte)      |         |
 * +-----------+--------------+---------+
 * ※ リトルエンディアン
 */
@Component
public class ResponseToBytesTransformer implements GenericTransformer<Response, byte[]> {

    private final short HEADER_SIZE = 4;

    @Override
    public byte[] transform(Response input) {

        System.out.println("ResponseToBytesTransformer input: " + input);

        ByteBuffer bf = ByteBuffer.allocate(HEADER_SIZE + input.getPayload().length).order(ByteOrder.LITTLE_ENDIAN);

        // タイプ設定
        bf.putShort((short)input.getType());

        // ペイロードサイズ設定
        bf.putShort((short)input.getPayload().length);

        // コンテンツセット
        bf.put(input.getPayload());

        var bytes = bf.array();

        System.out.println("ResponseToBytesTransformer output: " + Arrays.toString(bytes));

        return bytes;
    }
}

