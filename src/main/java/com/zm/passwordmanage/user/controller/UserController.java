package com.zm.passwordmanage.user.controller;


import com.zm.passwordmanage.common.Result;
import com.zm.passwordmanage.user.dto.UserDto;
import com.zm.passwordmanage.user.entity.User;
import com.zm.passwordmanage.user.service.Userservice;
import com.zm.passwordmanage.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Userservice userservice;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Result<?> register(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        try {
            userservice.register(username, password);
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
        return Result.success(null);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        try {
            User user = userservice.login(username, password);
            String token = jwtUtil.generateToken(user.getId());
            return Result.success(token);
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

}
