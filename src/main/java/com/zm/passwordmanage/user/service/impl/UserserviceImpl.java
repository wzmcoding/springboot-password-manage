package com.zm.passwordmanage.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.passwordmanage.user.entity.User;
import com.zm.passwordmanage.user.mapper.UserMapper;
import com.zm.passwordmanage.user.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserserviceImpl extends ServiceImpl<UserMapper, User> implements Userservice {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void register(String username, String password) {
        if (baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)) != null) {
            throw new RuntimeException("用户已存在");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        baseMapper.insert(user);
    }

    @Override
    public User login(String username, String password) {
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));

        if (user == null || !encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        return user;
    }
}
