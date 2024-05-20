package com.school.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterDTO implements Serializable {
    private String email;
    private String password;
    private String code;
}
