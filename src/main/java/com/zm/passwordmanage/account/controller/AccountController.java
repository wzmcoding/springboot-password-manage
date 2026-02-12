package com.zm.passwordmanage.account.controller;

import com.zm.passwordmanage.account.entity.Account;
import com.zm.passwordmanage.account.service.AccountService;
import com.zm.passwordmanage.common.Result;
import com.zm.passwordmanage.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public Result<?> add(@RequestBody Account account) throws Exception {
        accountService.add(account);
        return Result.success(null);
    }

    @GetMapping
    public Result<?> list() throws Exception {
        return Result.success(accountService.list());
    }

    /* 更新账号 */
    @PutMapping
    public Result<?> update(@RequestBody Account account) {
        Long userId = JwtInterceptor.USER_ID.get();
        account.setUserId(userId);
        try {
            accountService.update(account);
            return Result.success("更新成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /* 删除账号 */
    @DeleteMapping("{id}")
    public Result<?> delete(@PathVariable Long id) {
        Long userId = JwtInterceptor.USER_ID.get();
        try {
            accountService.delete(id, userId);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
}
