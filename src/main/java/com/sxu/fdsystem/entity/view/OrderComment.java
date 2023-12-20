package com.sxu.fdsystem.entity.view;

import lombok.Data;

@Data
public class OrderComment {

    private Integer orderId;

    private Integer commentScore;

    private String commentText;
}
