package com.sxu.fdsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxu.fdsystem.entity.OrderDish;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDishMapper extends BaseMapper<OrderDish> {
}
