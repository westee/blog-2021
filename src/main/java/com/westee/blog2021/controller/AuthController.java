package com.westee.blog2021.controller;

import com.westee.blog2021.entity.Result;
import com.westee.blog2021.entity.Status;
import com.westee.blog2021.entity.User;
import com.westee.blog2021.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/")
    public Result auth() {
        return Result.SuccessResult("已登录", null);
    }

    @PostMapping("/register")
    public Result register(UsernameAndPassword usernameAndPassword) {
        String username = usernameAndPassword.getUsername();
        String password = usernameAndPassword.getPassword();
        try {
            userService.createUser(username, password);
        } catch (DuplicateKeyException e) {
            return Result.FailResult("用户名已被注册", null);
        }
        return Result.SuccessResult("注册成功", null);
    }

    @PostMapping("/login")
    public Result login(UsernameAndPassword usernameAndPassword) {
        String password = usernameAndPassword.getPassword();
        String username = usernameAndPassword.getUsername();
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return Result.FailResult(e.getMessage(), null);
        }

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return Result.SuccessResult("登录成功", null);
        } catch (BadCredentialsException e) {
            return Result.FailResult("用户名或密码不正确", null);
        }
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
