package com.westee.blog2021.controller;

import com.westee.blog2021.entity.Result;
import com.westee.blog2021.entity.Status;
import com.westee.blog2021.service.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @GetMapping("/")
    public Result auth(){
        return Result.SuccessResult("已登录", new User(1, "老王"));
    }

    @PostMapping("/register")
    public Result register(UsernameAndPassword usernameAndPassword){
        return new Result(Status.OK, "", new User(1, "老王"));
    }

    private static class UsernameAndPassword {
        String username;
        String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
