package com.sxu.fdsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxu.fdsystem.entity.User;
import com.sxu.fdsystem.mapper.UserMapper;
import com.sxu.fdsystem.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
