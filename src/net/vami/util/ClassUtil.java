package net.vami.util;

import com.google.gson.JsonParseException;

public class ClassUtil {
    public static Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
    }
}
