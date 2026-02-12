package com.zm.passwordmanage.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zm.passwordmanage.user.entity.User;

public interface Userservice extends IService<User> {
    public void register(String username, String password);
    public User login(String username, String password);
}
