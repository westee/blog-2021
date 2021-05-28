package com.westee.blog2021.configuration;

import com.westee.blog2021.service.OrderService;
import com.westee.blog2021.service.UserService;
import com.westee.blog2021.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfiguration {
    @Bean
    public OrderService orderService(UserService userService){
        return new OrderService(userService);
    }

    @Bean
    public UserService userService(UserMapper userMapper){
        return new UserService(userMapper);
    }
}
