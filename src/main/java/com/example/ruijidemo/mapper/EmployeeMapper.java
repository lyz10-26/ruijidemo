package com.example.ruijidemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruijidemo.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
