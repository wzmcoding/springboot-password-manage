package com.zm.passwordmanage.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("account")
public class Account {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;
    private String title;
    private String account;
    private String password;
    private String note;
}
