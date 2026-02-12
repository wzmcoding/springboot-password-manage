package com.zm.passwordmanage.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.passwordmanage.account.entity.Account;
import com.zm.passwordmanage.account.mapper.AccountMapper;
import com.zm.passwordmanage.account.service.AccountService;
import com.zm.passwordmanage.interceptor.JwtInterceptor;
import com.zm.passwordmanage.util.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {


    @Autowired
    private AESUtil aesUtil;

    @Override
    public void add(Account account) throws Exception {
        account.setUserId(JwtInterceptor.USER_ID.get());
        account.setPassword(aesUtil.encrypt(account.getPassword()));
        baseMapper.insert(account);
    }

    @Override
    public List<Account> list() {
        List<Account> list = baseMapper.selectList(
                new LambdaQueryWrapper<Account>()
                        .eq(Account::getUserId, JwtInterceptor.USER_ID.get())
        );

        for (Account account : list) {
            try {
                account.setPassword(aesUtil.decrypt(account.getPassword()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }
}
