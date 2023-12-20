package com.sxu.fdsystem.entity.view;

import lombok.Data;

@Data
public class OrderDishes {

    private Integer orderId;

    private Integer dishId;

    private String dishName;

    private Float dishPrice;
}
