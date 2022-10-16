package dev.mikoto2000.study.springboot.integration.tcp.serializer.transformer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

import dev.mikoto2000.study.springboot.integration.tcp.serializer.model.Response;

@Component
public class BytesToResponseTransformer implements GenericTransformer<byte[], Response> {

    @Override
    public Response transform(byte[] input) {
        System.out.println("BytesToResponseTransformer input: " + Arrays.toString(input));

        var bb = ByteBuffer.wrap(input).order(ByteOrder.LITTLE_ENDIAN);

        var type = bb.getShort();
        var length = bb.getShort();
        var payload = new byte[length];
        bb.get(payload);

        var response = new Response(type, payload);

        System.out.println("BytesToResponseTransformer output: " + response);
        return response;
    }
}
