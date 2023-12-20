package com.sxu.fdsystem.entity.view;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeekCustFlow {

    private LocalDate dates;

    private Integer customers;
}
