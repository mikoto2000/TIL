package dev.mikoto2000.study.springboot.integration.tcp.serializer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private short type;
    private byte[] payload;
}
