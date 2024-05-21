package com.school.server.controller.web;

import com.school.pojo.dto.UserLoginDTO;
import com.school.pojo.dto.UserRegisterDTO;
import com.school.pojo.entity.User;
import com.school.pojo.vo.UserLoginVO;
import com.school.server.service.UserService;
import com.school.settings.Constant.JwtClaimsConstant;
import com.school.settings.Constant.MessageConstant;
import com.school.settings.excption.AccountNotFoundException;
import com.school.settings.excption.PasswordErrorException;
import com.school.settings.properties.JwtProperties;
import com.school.settings.result.Result;
import com.school.settings.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/web/user")
@Tag(name = "用户相关接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录接口")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("用户登录: {}",userLoginDTO);
        User user = userService.getUserByEmail(userLoginDTO.getEmail());
        if(user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //后台数据加密处理过了
        String password = DigestUtils.md5DigestAsHex(userLoginDTO.getPassword().getBytes());
        if(!user.getPassword().equals(password)){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //验证成功，生成token令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtils.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        //封装返回VO
        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(user,userLoginVO);
        userLoginVO.setToken(token);

        return Result.success(userLoginVO);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册接口")
    public Result<UserLoginVO> register(@RequestBody UserRegisterDTO userRegisterDTO){
        log.info("用户注册: {}",userRegisterDTO);
        User user = userService.registerUser(userRegisterDTO);

        //验证成功，生成token令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtils.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        //封装返回VO
        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(user,userLoginVO);
        userLoginVO.setToken(token);
        return Result.success(userLoginVO);
    }
}
