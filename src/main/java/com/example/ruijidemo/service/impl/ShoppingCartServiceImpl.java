package com.example.ruijidemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruijidemo.entity.ShoppingCart;
import com.example.ruijidemo.mapper.ShoppingCartMapper;
import com.example.ruijidemo.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
