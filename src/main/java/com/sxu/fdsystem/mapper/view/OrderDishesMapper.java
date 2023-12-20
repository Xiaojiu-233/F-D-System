package com.sxu.fdsystem.mapper.view;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxu.fdsystem.entity.view.OrderDishes;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDishesMapper extends BaseMapper<OrderDishes> {
}
