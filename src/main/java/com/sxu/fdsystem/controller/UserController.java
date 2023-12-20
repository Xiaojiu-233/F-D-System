package com.sxu.fdsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sxu.fdsystem.common.R;
import com.sxu.fdsystem.entity.Table;
import com.sxu.fdsystem.entity.User;
import com.sxu.fdsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    //查表
    @GetMapping("/list")
    public R<List<User>> list(){
        try{
            return R.success(userService.list());
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
    }

    //提交用户并去重(登录)
    @PostMapping
    public R<Integer> login(String username,String phone){
        LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(User::getPhoneNumber,phone);
        queryWrapper2.eq(User::getPhoneNumber,phone);
        queryWrapper2.eq(User::getName,username);
        if(userService.count(queryWrapper1) == 0 || userService.count(queryWrapper2) == 0){
            try{
                userService.save(new User(new Random().nextInt(Integer.MAX_VALUE),username,phone));
            }catch (Exception e){
                //读取失败的mysql报错结果
                return R.error(e.getCause().getMessage());
            }
        }
        Integer id = userService.getOne(queryWrapper2).getUserId();
        return R.success(id);
    }
}
