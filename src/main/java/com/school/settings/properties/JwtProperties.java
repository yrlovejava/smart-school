package com.school.settings.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "school.jwt")
@Data
public class JwtProperties {
    //用户生成jwt令牌的相关配置
    private String userSecretKey;//jwt密钥
    private long userTtl;//有效时间
    private String userTokenName;//生成令牌的名字
}
