package jp.dip.oyasirazu.test.msgpack.MsgpackTest;

import java.io.IOException;

import org.msgpack.MessagePack;
import org.msgpack.annotation.Message;
import org.msgpack.type.Value;

public class App {

    public static void main(String[] args) throws IOException {
        MessagePack mp = new MessagePack();

        System.out.println("=== ComplexMessage ===");

        // Serialize SimpleMessage.
        byte[] b = mp.write(new SimpleMessage());

        // Deserialize SimpleMessage(Value Object)
        Value value = mp.read(b);
        System.out.println(value);

        // Deserialize SimpleMessage(SimpleMessage Object)
        SimpleMessage sm = mp.read(b, SimpleMessage.class);
        System.out.println(sm.message);

        System.out.println("\n");

        System.out.println("=== ComplexMessage ===");

        // Serialize ComplexMessage.
        byte[] b2 = mp.write(new ComplexMessage());

        // Deserialize ComplexMessage(Value Object)
        Value value2 = mp.read(b2);
        System.out.println(value2);

        // Deserialize ComplexMessage(SimpleMessage Object)
        ComplexMessage cm = mp.read(b2, ComplexMessage.class);
        System.out.println(cm.title);
        System.out.println(cm.content);
        System.out.println(cm.extensionData[0]);
        System.out.println(cm.extensionData[1]);
        System.out.println(cm.extensionData[2]);

    }
}

@Message
class SimpleMessage {
    public String message = "Hello, World!";
}

@Message
class ComplexMessage {
    public String title = "TITLE";
    public String content = "CONTENT";
    public byte[] extensionData = new byte[] { 1, 2, 3 };
}