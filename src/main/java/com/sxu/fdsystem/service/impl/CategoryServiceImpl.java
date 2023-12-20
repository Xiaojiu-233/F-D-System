package com.sxu.fdsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxu.fdsystem.entity.Category;
import com.sxu.fdsystem.mapper.CategoryMapper;
import com.sxu.fdsystem.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
