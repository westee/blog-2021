package com.westee.blog2021.controller;

import com.westee.blog2021.entity.LoginResult;
import com.westee.blog2021.entity.Result;
import com.westee.blog2021.entity.Status;
import com.westee.blog2021.entity.User;
import com.westee.blog2021.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/auth")
    public LoginResult auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.getUserByUsername(authentication == null ? null : authentication.getName());

        if (user == null) {
            return LoginResult.success( "用户未登录", false);
        } else {
            return LoginResult.success("登录成功", true, user);
        }
    }

    @PostMapping("/auth/register")
    public LoginResult register(@RequestBody UsernameAndPassword usernameAndPassword) {
        String username = usernameAndPassword.getUsername();
        String password = usernameAndPassword.getPassword();
        try {
            userService.createUser(username, password);
        } catch (DuplicateKeyException e) {
            return LoginResult.failure("用户名已被注册", false);
        }
        return LoginResult.failure("注册成功", false);
    }

    @PostMapping("/auth/login")
    public Result login(@RequestBody UsernameAndPassword usernameAndPassword) {
        String password = usernameAndPassword.getPassword();
        String username = usernameAndPassword.getUsername();
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return LoginResult.failure(e.getMessage(), false);
        }

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return LoginResult.success("登录成功", true);
        } catch (BadCredentialsException e) {
            return LoginResult.failure("用户名或密码不正确", false);
        }
    }

    @GetMapping("/auth/logout")
    public Result logout(UsernameAndPassword usernameAndPassword) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        if(name == null) {
            return LoginResult.failure("用户未登录", false);
        } else {
            SecurityContextHolder.clearContext();
            return LoginResult.success("退出成功", false);
        }
    }

    private static class UsernameAndPassword {
        String username;
        String password;

        public UsernameAndPassword(String username, String password) {
            this.username = username;
            this.password = password;
        }

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
