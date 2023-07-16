package com.example.ruijidemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruijidemo.entity.User;
import com.example.ruijidemo.mapper.UserMapper;
import com.example.ruijidemo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
