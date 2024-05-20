package com.school.settings.Constant;

import java.util.concurrent.TimeUnit;

/**
 * redis相关的属性
 */
public class RedisConstant {
    //验证码的有效时间
    public static final long CODE_TIME_LIMIT_VALUE = 3;

    //验证码有效时间的单位
    public static final TimeUnit CODE_TIME_LIMIT_TYPE = TimeUnit.MINUTES;

    //验证码的key
    public static final String CODE_KEY = "-code";

}
