import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Main
 */
public class Main {
    public static void main(String args[]) {

        var bb = ByteBuffer.allocate(6).order(ByteOrder.LITTLE_ENDIAN);
        bb.putShort((short)0);
        bb.putShort((short)65535);
        bb.putShort((short)0xF0E0);
        System.out.println("bb.array(): " + Arrays.toString(bb.array()));
        // => byte[6] { 0, 0, -1, -1, -32, -16 }

        System.out.println("bb.getShort(4): " + bb.getShort(4));
        // => -3872

        System.out.println("Short.toUnsignedInt(bb.getShort(4)): " + Short.toUnsignedInt(bb.getShort(4)));
        // => 61664

        System.out.println("bb.get(4): " + bb.get(4));
        // => -32

        System.out.println("Byte.toUnsignedInt(bb.get(4)): " + Byte.toUnsignedInt(bb.get(4)));
        // => 224

        System.out.println("bb.get(5): " + bb.get(5));
        // => -16

        System.out.println("Byte.toUnsignedInt(bb.get(5)): " + Byte.toUnsignedInt(bb.get(5)));
        // => 240

    }
}
