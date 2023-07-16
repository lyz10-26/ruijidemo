package com.example.ruijidemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruijidemo.common.R;
import com.example.ruijidemo.entity.Category;
import com.example.ruijidemo.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 添加操作
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {

        log.info("category:{}", category);
        categoryService.save(category);

        return R.success("成功");

    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {

        //分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();

        //按照升序排序
        wrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, wrapper);

        return R.success(pageInfo);
    }


    @DeleteMapping
    public R<String> delete(Long ids) {

//        categoryService.removeById(id);
        categoryService.remove(ids);

        return R.success("删除成功");
    }


    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改分类信息：{}", category);
        categoryService.updateById(category);
        return R.success("修改成功");
    }


    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        //条件构造器
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件
        lambdaQueryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        //添加排序条件
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByAsc(Category::getUpdateTime);

        List<Category> list = categoryService.list(lambdaQueryWrapper);
        return R.success(list);


    }

}
