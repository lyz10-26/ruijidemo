package com.example.ruijidemo.dto;

import com.example.ruijidemo.entity.Setmeal;
import com.example.ruijidemo.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
