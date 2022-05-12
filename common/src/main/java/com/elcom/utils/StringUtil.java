package com.elcom.utils;

import java.util.UUID;

public class StringUtil {
    public static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isNumberic(String number) {
        if(number == null || "".equals(number)) {
            return false;
        }
        char ch_max = (char) 0x39;
        char ch_min = (char) 0x30;

        for(int i = 0; i< number.length(); i++) {
            char ch = number.charAt(i);
            if((ch< ch_min) || (ch > ch_max)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUUID(String string) {
        if(isNullOrEmpty(string)) {
            return false;
        }
        try {
            UUID.fromString(string);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
