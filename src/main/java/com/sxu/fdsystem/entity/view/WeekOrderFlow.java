package com.sxu.fdsystem.entity.view;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeekOrderFlow {

    private LocalDate dates;

    private Integer orders;
}
