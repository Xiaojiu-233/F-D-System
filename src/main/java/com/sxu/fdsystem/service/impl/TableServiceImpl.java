package com.sxu.fdsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxu.fdsystem.entity.Table;
import com.sxu.fdsystem.mapper.TableMapper;
import com.sxu.fdsystem.service.TableService;
import org.springframework.stereotype.Service;

@Service
public class TableServiceImpl extends ServiceImpl<TableMapper,Table> implements TableService {
}
