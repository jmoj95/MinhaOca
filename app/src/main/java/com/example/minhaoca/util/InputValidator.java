package com.example.minhaoca.util;

public interface InputValidator {

    public Integer verifyLogin(String login);
    public Integer verifyEmail(String email);
    public Integer verifyPhone(String phone);
    public Integer verifyPassword(String password);
    public Integer verifyPasswordRepeat(String passwordRepeat, String password);

}
