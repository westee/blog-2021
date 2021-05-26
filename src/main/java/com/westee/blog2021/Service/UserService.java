package com.westee.blog2021.Service;

import com.westee.blog2021.mapper.UserMapper;

import javax.inject.Inject;

public class UserService {
    private UserMapper userMapper;

    @Inject
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserById(int id) {
        return userMapper.findUserById(id);
    }
}
