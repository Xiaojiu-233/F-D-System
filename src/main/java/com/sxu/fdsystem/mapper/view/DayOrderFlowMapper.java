package com.sxu.fdsystem.mapper.view;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxu.fdsystem.entity.view.DayCustFlow;
import com.sxu.fdsystem.entity.view.DayOrderFlow;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DayOrderFlowMapper extends BaseMapper<DayOrderFlow> {
}
