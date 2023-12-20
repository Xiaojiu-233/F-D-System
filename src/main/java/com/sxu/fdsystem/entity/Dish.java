package com.sxu.fdsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("Dish")
public class Dish {

    @TableId
    private Integer dishId;

    private String name;

    private Float price;

    private String imgUrl;

    private Integer category;
}
