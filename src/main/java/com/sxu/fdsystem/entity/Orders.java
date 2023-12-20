package com.sxu.fdsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Orders")
public class Orders {

    @TableId
    private Integer orderId;

    private String note;

    private LocalDateTime preorderTime;

    private LocalDateTime orderTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Float amount;

    private Float payAmount;

    private String status;

    private Integer peopleNum;

    private Integer userId;

    private Integer score;

    private String commentText;

}
