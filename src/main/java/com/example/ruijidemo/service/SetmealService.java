package com.example.ruijidemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ruijidemo.dto.SetmealDto;
import com.example.ruijidemo.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    //新增套餐同时保存菜品和套餐的关系
    public void saveWithDish(SetmealDto setmealDto);
    //删除
    public void removeWithDish(List<Long> ids);
}
