package com.zm.passwordmanage.user.controller;


import com.zm.passwordmanage.common.Result;
import com.zm.passwordmanage.user.entity.User;
import com.zm.passwordmanage.user.service.Userservice;
import com.zm.passwordmanage.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Userservice userservice;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Result<?> register(@RequestParam String username, @RequestParam String password) {
        userservice.register(username, password);
        return Result.success(null);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestParam String username, @RequestParam String password) {
        User user = userservice.login(username, password);
        String token = jwtUtil.generateToken(user.getId());
        return Result.success(token);
    }

}
