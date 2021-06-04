package com.westee.blog2021.service;

import com.westee.blog2021.entity.User;
import com.westee.blog2021.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserMapper userMapper;

    @Mock
    BCryptPasswordEncoder encoder;

    @InjectMocks
    UserService userService;

    @Test
    void getUserByUsername() {
        userService.getUserByUsername("老王");
        verify(userMapper).getUserByUsername("老王");
    }

    @Test
    void createUser() {
        when(encoder.encode("123456")).thenReturn("666");
        userService.createUser("123456", "123456");
        verify(userMapper).createUser("123456", "666");
    }

    @Test
    void throwExceptionWhenUserNotFound(){
        Assertions.assertThrows(UsernameNotFoundException.class, ()-> userService.loadUserByUsername("123"));
    }

    @Test
    void returnUserDetailWhenUserFound(){
        when(userMapper.getUserByUsername("laowang")).thenReturn(new User(1, "laowang", "laowang"));
        UserDetails userDetails = userService.loadUserByUsername("laowang");

        Assertions.assertEquals("laowang", userDetails.getUsername());
        Assertions.assertEquals("laowang", userDetails.getPassword());
    }
}