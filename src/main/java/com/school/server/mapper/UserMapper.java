package com.school.server.mapper;

import com.school.pojo.entity.User;
import com.school.server.annotation.AutoFill;
import com.school.settings.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    @Select("select * from public.user where email = #{eamil}")
    User selectUserByEmail(String email);

    /**
     * 新增用户
     * @param user
     * @return
     */
    @AutoFill(OperationType.INSERT)
    @Insert("insert into public.user " +
            "(id, username, email, password, status, role, create_time, create_user, update_time, update_user) values " +
            "(#{id},#{username},#{email},#{password},#{status},#{role},#{createTime},#{createUser},#{updateTime},#{updateUser})")
    Integer insertUser(User user);
}
