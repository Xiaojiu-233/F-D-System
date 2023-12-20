package com.sxu.fdsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sxu.fdsystem.common.R;
import com.sxu.fdsystem.entity.view.*;
import com.sxu.fdsystem.mapper.view.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/backend")
@Slf4j
public class BackendController {

    //数据收集汇总
    //订单菜品
    @Autowired
    private OrderDishesMapper mapper5;
    @GetMapping("/od")
    public R<List<OrderDishes>> list5 (){
        return R.success(mapper5.selectList(new LambdaQueryWrapper<>()));
    }

    //评论
    @Autowired
    private OrderCommentMapper mapper6;
    @GetMapping("/oc")
    public R<List<OrderComment>> list6 (){
        return R.success(mapper6.selectList(new LambdaQueryWrapper<>()));
    }

    //昨天每小时的订单流量
    @Autowired
    private DayOrderFlowMapper mapper1;
    @GetMapping("/dayOrder")
    public R<List<DayOrderFlow>> list1 (){
        return R.success(mapper1.selectList(new LambdaQueryWrapper<>()));
    }

    //昨天每小时的客流量
    @Autowired
    private DayCustFlowMapper mapper2;
    @GetMapping("/dayCust")
    public R<List<DayCustFlow>> list2 (){
        return R.success(mapper2.selectList(new LambdaQueryWrapper<DayCustFlow>()));
    }

    //最近一周每天的订单流量
    @Autowired
    private WeekOrderFlowMapper mapper3;
    @GetMapping("/weekOrder")
    public R<List<WeekOrderFlow>> list3 (){
        return R.success(mapper3.selectList(new LambdaQueryWrapper<WeekOrderFlow>()));
    }

    //最近一周每天的客流量
    @Autowired
    private WeekCustFlowMapper mapper4;
    @GetMapping("/weekCust")
    public R<List<WeekCustFlow>> list4 (){
        return R.success(mapper4.selectList(new LambdaQueryWrapper<WeekCustFlow>()));
    }

}
