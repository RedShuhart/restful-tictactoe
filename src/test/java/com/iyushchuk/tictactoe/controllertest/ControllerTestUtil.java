package com.iyushchuk.tictactoe.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ControllerTestUtil {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
