package com.iyushchuk.tictactoe.common.util;

import java.util.UUID;

public class UUIDGenerator {
    public static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}
