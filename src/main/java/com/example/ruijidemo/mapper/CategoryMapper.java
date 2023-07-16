package com.example.ruijidemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruijidemo.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
