package com.school.server.controller;

import com.school.settings.Constant.EmailConstant;
import com.school.settings.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/code")
    public Result<String> getCode(String email){
        log.info("获取验证码: {}",email);
        //随机生成验证码
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        //封装消息格式和内容
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(EmailConstant.EMAIL_FROM);
        message.setText(code + EmailConstant.EMAIL_USEFUL_TIME_RECOMMEND);
        message.setFrom(EmailConstant.EMAIL_FROM);
        message.setTo(email);
        //发送邮件
        sender.send(message);

        return Result.success(String.valueOf(code));
    }
}
