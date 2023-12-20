package com.sxu.fdsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sxu.fdsystem.common.R;
import com.sxu.fdsystem.entity.Table;
import com.sxu.fdsystem.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/table")
@Slf4j
public class TableController {
    
    @Autowired
    private TableService tableService;

    //查表
    @GetMapping("/list")
    public R<List<Table>> list(){
        try{
            return R.success(tableService.list());
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
    }

    //增
    @PostMapping
    public R<String> add(int maxSeats){
        try{
            tableService.save(new Table(new Random().nextInt(Integer.MAX_VALUE),null,maxSeats,null));
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
        return  R.success("添加成功!");
    }


    //删
    @PostMapping("/del/{tableId}")
    public R<String> del(@PathVariable Integer tableId){
        try{
            tableService.removeById(tableId);
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
        return  R.success("删除成功!");
    }
}
