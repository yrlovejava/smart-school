package com.school.server.service;

import com.school.pojo.dto.UserRegisterDTO;
import com.school.pojo.entity.User;

public interface UserService {

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    User getUserByEmail(String email);

    /**
     * 新增用户(注册用户)
     * @param userRegisterDTO
     * @return
     */
    User registerUser(UserRegisterDTO userRegisterDTO);
}
