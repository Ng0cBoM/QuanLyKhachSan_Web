package com.hotel.demo.util;

import java.util.Objects;

public class DataUtil {

    public static int safeToInt(Object obj) {

        if (!isNumber(obj)) {
            return -1;
        }
        return Integer.valueOf(obj.toString());
    }

    public static String safeToString(Object obj) {
        if (Objects.isNull(obj)) {
            return "";
        }
        return obj.toString();
    }

    public static float safeToFloat(Object obj) {
        if (!isFloat(obj)) {
            return -1F;
        }
        return Float.valueOf(obj.toString());
    }

    public static boolean isNumber(Object ob) {
        try {
            Integer.valueOf(ob.toString());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isFloat(Object object) {
        try {
            Float.valueOf(object.toString());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}