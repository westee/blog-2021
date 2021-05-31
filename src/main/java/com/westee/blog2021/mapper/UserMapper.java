package com.westee.blog2021.mapper;

import com.westee.blog2021.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findUserById(@Param("id") int id);

    @Insert("insert into user ( username, encrypted_password) values(#{username}, #{encryptedPassword})")
    void createUser(String username, String encryptedPassword);

    @Select("select * from user where username = #{username}")
    User getUserByUsername(@Param("username") String username);

}
