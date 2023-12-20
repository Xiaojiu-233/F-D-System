package com.sxu.fdsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sxu.fdsystem.common.R;
import com.sxu.fdsystem.entity.Category;
import com.sxu.fdsystem.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //查表
    @GetMapping("/list")
    public R<List<Category>> list(){
        try{
            LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByDesc(Category::getSort);
            return R.success(categoryService.list(queryWrapper));
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
    }

    //查单体
    @GetMapping
    public R<Category> get(int categoryId){
        try{
            return R.success(categoryService.getById(categoryId));
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
    }

    //增
    @PostMapping
    public R<String> add(String name,int sort){
        try{
            categoryService.save(new Category(new Random().nextInt(Integer.MAX_VALUE),name,sort));
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
        return  R.success("添加成功!");
    }

    //删
    @PostMapping("/del/{categoryId}")
    public R<String> del(@PathVariable Integer categoryId){
        try{
            categoryService.removeById(categoryId);
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
        return  R.success("删除成功!");
    }

    //改
    @PostMapping("/update")
    public R<String> update(int categoryId,String name,int sort){
        try{
            categoryService.updateById(new Category(categoryId,name,sort));
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
        return  R.success("修改成功!");
    }


}
