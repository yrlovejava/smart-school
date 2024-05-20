package com.school.settings.excption;

/**
 * 验证码过期提醒
 */
public class CodeErrorException extends BaseException{
    public CodeErrorException(){

    }

    public CodeErrorException(String msg){
        super(msg);
    }
}
