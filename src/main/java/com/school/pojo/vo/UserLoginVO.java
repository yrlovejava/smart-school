package com.school.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder//开启建造者模式
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO implements Serializable {

    private String id;

    private String username;

    private String token;
}
