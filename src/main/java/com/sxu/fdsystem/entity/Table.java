package com.sxu.fdsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("table_")
public class Table {

    @TableId
    private Integer tableId;

    private Integer orderId;

    private Integer maxSeats;

    private Integer curSeats;
}
