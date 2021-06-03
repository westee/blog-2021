package com.westee.blog2021.service;

import com.westee.blog2021.entity.Result;
import com.westee.blog2021.entity.User;
import com.westee.blog2021.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private UserMapper userMapper;
    private BCryptPasswordEncoder encoder;

    @Inject
    public UserService(UserMapper userMapper, BCryptPasswordEncoder encoder) {
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    public User getUserById(int id) {
        return userMapper.findUserById(id);
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public void createUser (String username, String password) {
        userMapper.createUser(username, encoder.encode(password));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(s);
        if(user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new org.springframework.security.core.userdetails.User(
                s,
                user.getEncryptedPassword(),
                Collections.emptyList());
    }
}
