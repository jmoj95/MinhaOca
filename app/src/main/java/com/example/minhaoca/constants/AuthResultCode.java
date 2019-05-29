package com.example.minhaoca.constants;

public final class AuthResultCode {


    public static Integer SIGNUP_SUCCESS = 0;
    public static Integer SIGNUP_FAILURE = 1;

    private static Integer currentResultCode = -1;

    public static Integer getCurrentResultCode() {
        return currentResultCode;
    }

    public static void setCurrentResultCode(Integer currentResultCode) {
        AuthResultCode.currentResultCode = currentResultCode;
    }
}
