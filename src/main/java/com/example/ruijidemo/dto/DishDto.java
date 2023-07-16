package com.example.ruijidemo.dto;


import com.example.ruijidemo.entity.Dish;
import com.example.ruijidemo.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
