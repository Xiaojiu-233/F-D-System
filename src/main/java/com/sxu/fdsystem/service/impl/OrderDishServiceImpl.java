package com.sxu.fdsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxu.fdsystem.entity.OrderDish;
import com.sxu.fdsystem.mapper.OrderDishMapper;
import com.sxu.fdsystem.service.OrderDishService;
import org.springframework.stereotype.Service;

@Service
public class OrderDishServiceImpl extends ServiceImpl<OrderDishMapper, OrderDish> implements OrderDishService {
}
