package com.example.ruijidemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruijidemo.common.CustomException;
import com.example.ruijidemo.entity.Category;
import com.example.ruijidemo.entity.Dish;
import com.example.ruijidemo.entity.Setmeal;
import com.example.ruijidemo.mapper.CategoryMapper;
import com.example.ruijidemo.service.CategoryService;
import com.example.ruijidemo.service.DishService;
import com.example.ruijidemo.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dish=new LambdaQueryWrapper<>();
        dish.eq(Dish::getCategoryId,id);
        int count1 = (int) dishService.count(dish);
        //查询当前菜单是否关联了菜品和套餐，如果关联则抛出异常
        if(count1>0){
            throw new CustomException("当前分类有关联，不能删除");

        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2=(int) setmealService.count(setmealLambdaQueryWrapper);
        if(count2>0){
            throw new CustomException("当前分类有关联，不能删除");

        }
        //正常删除
        super.removeById(id);


    }
}
