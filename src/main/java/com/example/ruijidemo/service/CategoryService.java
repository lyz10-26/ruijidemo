package com.example.ruijidemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ruijidemo.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
