package com.school.server.controller;

import com.school.settings.Constant.EmailConstant;
import com.school.settings.Constant.RedisConstant;
import com.school.settings.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/common")
@Tag(name = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private MailSender sender;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/code")
    public Result getCode(String email){
        log.info("获取验证码: {}",email);
        //随机生成验证码
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        //加入redis中
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(email + RedisConstant.CODE_KEY,code, RedisConstant.CODE_TIME_LIMIT_VALUE,RedisConstant.CODE_TIME_LIMIT_TYPE);

        //封装消息格式和内容
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(EmailConstant.EMAIL_FROM);
        message.setText(code + EmailConstant.EMAIL_USEFUL_TIME_RECOMMEND);
        message.setFrom(EmailConstant.EMAIL_FROM);
        message.setTo(email);
        //发送邮件
        sender.send(message);

        return Result.success();
    }

}
