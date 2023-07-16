package com.example.ruijidemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruijidemo.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}
