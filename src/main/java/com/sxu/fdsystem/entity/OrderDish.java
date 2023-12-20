package com.sxu.fdsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("Order_Dish")
public class OrderDish {

    private Integer dishId;

    private Integer orderId;

    private String size;

}
