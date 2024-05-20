package com.school.server.mapper;

import com.school.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where email = #{eamil}")
    User selectUserByEmail(String email);
}
