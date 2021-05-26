package com.westee.blog2021.Service;

import javax.inject.Inject;

public class OrderService {
    private UserService userService;

    @Inject
    public OrderService(UserService userService) {
        this.userService = userService;
    }

    void placeOrder(int userId) {
        userService.getUserById(userId);
    }
}
