package com.school.server.service.impl;

import com.school.pojo.entity.User;
import com.school.server.mapper.UserMapper;
import com.school.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }
}
