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

    @Override
    public void update(Account account) throws Exception {
        if (account.getId() == null) {
            throw new RuntimeException("ID不能为空");
        }

        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", account.getId())
                    .eq("user_id", account.getUserId());

        Account existingAccount = baseMapper.selectOne(queryWrapper);
        if (existingAccount == null) {
            throw new RuntimeException("账号不存在或不属于当前用户");
        }

        // 🔥 重新加密
        account.setPassword(aesUtil.encrypt(account.getPassword()));

        baseMapper.updateById(account);
    }

    @Override
    public void delete(Long id, Long userId) {

        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id)
                    .eq("user_id", userId);

        Account existingAccount = baseMapper.selectOne(queryWrapper);
        if (existingAccount == null) {
            throw new RuntimeException("账号不存在或不属于当前用户");
        }

        baseMapper.deleteById(id);
    }
}
