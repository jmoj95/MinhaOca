package com.example.minhaoca.util;

import static com.example.minhaoca.constants.InputValidationCode.*;

public class ConcreteInputValidator implements InputValidator {

    @Override
    public Integer verifyLogin(String login) {
        if (login.contains(" ")) {
            return LOGIN_CONTAINS_SPACES;
        }
        if (login.trim().length() == 0) {
            return LOGIN_IS_EMPTY;
        }
        return FIELD_OK;
    }

    @Override
    public Integer verifyEmail(String email) {
        if (email.contains(" ")) {
            return EMAIL_CONTAINS_SPACES;
        }
        if (email.trim().length() == 0) {
            return EMAIL_IS_EMPTY;
        }
        if (!email.contains("@")) {
            return EMAIL_WRONG_FORMAT;
        }
        if (email.indexOf("@") != email.lastIndexOf("@")) {
            return EMAIL_WRONG_FORMAT;
        }
        return FIELD_OK;
    }

    @Override
    public Integer verifyPhone(String phone)
    {
        if (phone.trim().length() == 0) {
            return PHONE_IS_EMPTY;
        }
        return FIELD_OK;
    }

    @Override
    public Integer verifyPassword(String password) {
        if (password.contains(" ")) {
            return PASSWORD_CONTAINS_SPACES;
        }
        if (password.trim().length() == 0) {
            return PASSWORD_IS_EMPTY;
        }
        if (password.length() < 6) {
            return PASSWORD_TOO_SHORT;
        }
        return FIELD_OK;
    }

    @Override
    public Integer verifyPasswordRepeat(String passwordRepeat, String password) {
        if (!passwordRepeat.equals(password)) {
            return PASSWORD_REPEAT_DOESNT_MATCH;
        }
        return FIELD_OK;
    }

}
