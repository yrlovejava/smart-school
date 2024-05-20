package com.school.server.service;

import com.school.pojo.entity.User;

public interface UserService {

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    User getUserByEmail(String email);
}
