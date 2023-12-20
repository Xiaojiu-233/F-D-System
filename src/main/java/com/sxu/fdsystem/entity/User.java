package com.sxu.fdsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("User")
public class User {

    @TableId
    private Integer userId;

    private String name;

    private String phoneNumber;
}
