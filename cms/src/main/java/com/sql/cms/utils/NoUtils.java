package com.sql.cms.utils;

import java.util.UUID;

public class NoUtils {
    public static String CreateRandomNo(String prefix) {
        String currentTime = String.valueOf(System.currentTimeMillis());

        return prefix
                + UUID.randomUUID().toString().substring(30)
                + currentTime.substring(currentTime.length() - 6);
    }
}
