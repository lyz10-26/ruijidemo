package com.example.ruijidemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruijidemo.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
