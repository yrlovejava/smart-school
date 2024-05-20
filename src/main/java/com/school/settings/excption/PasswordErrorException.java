package com.school.settings.excption;

public class PasswordErrorException extends BaseException{

    public PasswordErrorException() {
    }

    public PasswordErrorException(String msg) {
        super(msg);
    }
}
