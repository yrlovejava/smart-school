package com.school.server.service.impl;

import com.school.pojo.dto.UserRegisterDTO;
import com.school.pojo.entity.User;
import com.school.server.mapper.UserMapper;
import com.school.server.service.UserService;
import com.school.settings.Constant.MessageConstant;
import com.school.settings.Constant.RedisConstant;
import com.school.settings.Constant.RoleConstant;
import com.school.settings.Constant.StatusConstant;
import com.school.settings.excption.CodeErrorException;
import com.school.settings.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 新增用户(注册用户)
     * @param userRegisterDTO
     */
    @Override
    public User registerUser(UserRegisterDTO userRegisterDTO) {
        //验证验证码是否正确
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String email = userRegisterDTO.getEmail();
        String code = String.valueOf(valueOperations.get(email + RedisConstant.CODE_KEY));
        if(code == null){
            throw new CodeErrorException(MessageConstant.CODE_EXPIRED_ERROR);
        }
        String getCode = userRegisterDTO.getCode();
        if(!getCode.equals(code)){
            throw new CodeErrorException(MessageConstant.CODE_ERROR);
        }

        //封装user
        User user = new User();
        user.setEmail(email);
        //密码加密保存
        user.setPassword(DigestUtils.md5DigestAsHex(userRegisterDTO.getPassword().getBytes()));
        user.setId(UUIDUtils.getUUID());
        user.setRole(RoleConstant.USER_ROLE_USER);
        user.setStatus(StatusConstant.STATUS_NO_AUTHENTICATION);
        //随机生成名字
        user.setUsername(UUID.randomUUID().toString());

        //调用mapper
        userMapper.insertUser(user);

        return user;
    }
}
