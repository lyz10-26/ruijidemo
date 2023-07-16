package com.example.ruijidemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruijidemo.entity.Orders;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
