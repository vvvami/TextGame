package net.vami.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public class HexUtil {
    public static String toHex(String arg) {
        return String.format("%x", new BigInteger(1, arg.getBytes(StandardCharsets.UTF_8)));
    }

    public static String fromHex(String arg) {
        byte[] bytes = HexFormat.of().parseHex(arg);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
