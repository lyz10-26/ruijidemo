package com.example.ruijidemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ruijidemo.common.BaseContext;
import com.example.ruijidemo.common.R;
import com.example.ruijidemo.entity.ShoppingCart;
import com.example.ruijidemo.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;


    /**
     * 添加购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车信息={}", shoppingCart);
        //设置用户id
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);


        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, currentId);


        Long dishId = shoppingCart.getDishId();
        if (dishId != null) {
            //当前为菜品
            lambdaQueryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }


        //查询当前菜品或者套餐是不是在购物车里面
        ShoppingCart cartServiceOne = shoppingCartService.getOne(lambdaQueryWrapper);
        //如果已经存在，在原来数量加1，不存在数量为1
        if (cartServiceOne != null) {
            //获取原先的数量加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            log.info("数据={}", cartServiceOne);
            shoppingCartService.updateById(cartServiceOne);
        } else {

            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }

        return R.success(cartServiceOne);
    }

    /**
     * 删除购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        //获取套餐id
        Long setmealId = shoppingCart.getSetmealId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        if (setmealId != null) {
            //当前为套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
        } else {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);
        Integer number = one.getNumber();
        if (number == 1) {
            shoppingCartService.remove(queryWrapper);
        } else {
            one.setNumber(number - 1);
            shoppingCartService.updateById(one);
        }

        return R.success(one);
    }


    /**
     * 查看购物车
     *
     * @return
     */


    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {

        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, currentId);
        lambdaQueryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> shopCartList = shoppingCartService.list(lambdaQueryWrapper);

        return R.success(shopCartList);
    }


    /**
     * 清空购物车
     *
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        //SQL:delete from shopping_cart where user_id=?

        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        //清空购物车
        shoppingCartService.remove(lambdaQueryWrapper);
        return R.success("清空成功");

    }

}
