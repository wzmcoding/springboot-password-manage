package com.zm.passwordmanage.account.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zm.passwordmanage.account.entity.Account;

import java.util.List;

public interface AccountService extends IService<Account> {
    public void add(Account account) throws Exception;
    public List<Account> list();
    public void update(Account account) throws Exception;
    public void delete(Long id, Long userId);
}
