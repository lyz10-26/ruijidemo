package com.example.ruijidemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ruijidemo.dto.DishDto;
import com.example.ruijidemo.entity.Dish;
import com.example.ruijidemo.mapper.DishMapper;

public interface DishService extends IService<Dish> {

    /**
     * 新增菜品，同时插入口味
     */
    public void saveWithFlavor(DishDto dishDto);

    /**
     * 根据id查询菜品信息和对应的口味
     **/
    public DishDto getByIdWithFlavor(Long id);

     public void updateWithFlavor(DishDto dishDto);
}
