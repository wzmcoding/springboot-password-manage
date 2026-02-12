package com.zm.passwordmanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({
   "com.zm.passwordmanage.account.mapper",
   "com.zm.passwordmanage.user.mapper"
})
public class PasswordManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(PasswordManageApplication.class, args);
    }

}
