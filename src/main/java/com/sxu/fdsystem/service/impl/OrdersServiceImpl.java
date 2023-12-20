package com.sxu.fdsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxu.fdsystem.entity.Orders;
import com.sxu.fdsystem.mapper.OrdersMapper;
import com.sxu.fdsystem.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
}
