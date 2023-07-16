package com.example.ruijidemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruijidemo.entity.Employee;
import com.example.ruijidemo.mapper.EmployeeMapper;
import com.example.ruijidemo.service.EmployeeService;
import org.springframework.stereotype.Service;
/**
 * @author lyz
 * @DATE 2023-07-03 15:41
 */
@Service
public class EmployeeServiceImpl  extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
