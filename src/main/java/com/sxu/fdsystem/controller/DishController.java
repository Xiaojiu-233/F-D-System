package com.sxu.fdsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sxu.fdsystem.common.R;
import com.sxu.fdsystem.entity.Dish;
import com.sxu.fdsystem.entity.User;
import com.sxu.fdsystem.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    //查表
    @GetMapping("/list")
    public R<List<Dish>> list(){
        try{
            return R.success(dishService.list());
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
    }

    //查单体
    @GetMapping
    public R<Dish> get(int dishId){
        try{
            return R.success(dishService.getById(dishId));
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
    }

    //增
    @PostMapping
    public R<String> add(String name,String imgUrl,float price,int categoryId){
        try{
            dishService.save(new Dish(new Random().nextInt(Integer.MAX_VALUE),name,price,imgUrl,categoryId));
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
        return  R.success("添加成功!");
    }

    //改
    @PostMapping("/update")
    public R<String> update(int dishId,String name,String imgUrl,float price,int categoryId){
        try{
            dishService.updateById(new Dish(dishId,name,price,imgUrl,categoryId));
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
        return  R.success("修改成功!");
    }
}
