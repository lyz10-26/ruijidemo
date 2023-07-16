package com.example.ruijidemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruijidemo.common.CustomException;
import com.example.ruijidemo.dto.SetmealDto;
import com.example.ruijidemo.entity.Setmeal;
import com.example.ruijidemo.entity.SetmealDish;
import com.example.ruijidemo.mapper.SetmealMappper;
import com.example.ruijidemo.service.SetmealDishService;
import com.example.ruijidemo.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMappper, Setmeal> implements SetmealService {


    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {

        //保存基本信息
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map(item -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存关系信息
        setmealDishService.saveBatch(setmealDishes);

    }

    @Override
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态,确认是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);

        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new CustomException("正在售卖，禁止删除");
        }
        //不能的话，就抛异常
        this.removeByIds(ids);

        //删除关系

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper);


    }

}
