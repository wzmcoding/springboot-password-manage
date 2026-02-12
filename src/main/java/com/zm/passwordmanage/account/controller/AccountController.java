package com.zm.passwordmanage.account.controller;

import com.zm.passwordmanage.account.entity.Account;
import com.zm.passwordmanage.account.service.AccountService;
import com.zm.passwordmanage.common.Result;
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
}
