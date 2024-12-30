package net.vami.util;

public class LogUtil {
    public static void Log(LoggerType loggerType, String text, Object ... format) {
        System.out.printf("[Logger - %s]: %s%n", loggerType.name(), text.formatted(format));
    }

    public static void Log(String text, Object ... format) {
        System.out.printf("[Logger - %s]: %s%n", LoggerType.INFO.name(), text.formatted(format));
    }
}
