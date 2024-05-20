package com.school.settings.excption;

public class UserNotLoginException extends BaseException{
    public UserNotLoginException() {
    }

    public UserNotLoginException(String msg) {
        super(msg);
    }
}
