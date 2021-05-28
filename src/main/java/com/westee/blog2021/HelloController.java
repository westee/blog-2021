package com.westee.blog2021;

import com.westee.blog2021.service.User;
import com.westee.blog2021.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class HelloController {
    private UserService userService;

    @Inject
    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public User index(){
        return userService.getUserById(1);
    }
}
