package com.example.ruijidemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruijidemo.common.R;
import com.example.ruijidemo.entity.Employee;
import com.example.ruijidemo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author lyz
 * &#064;DATE  2023-07-03 15:44
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        //将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //查数据库
        LambdaQueryWrapper<Employee> querywapper = new LambdaQueryWrapper<>();
        querywapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(querywapper);
        if (emp == null) {
            return R.error("登录失败");
        }
        //密码比对
        if (!emp.getPassword().equals(password)) {

            return R.error("密码错误");
        }
        //查看状态
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }
        //成功
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     *
     * @return
     */

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("员工信息:{}", employee.toString());
        //设置初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("新增成功");

    }

    /**
     * 分页信息
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name) {
        log.info("page={},pageSize={},name={}", page, pageSize, name);
        //构造分页构造器
        Page<Employee> pageInfo = new Page<Employee>(page, pageSize);

        //条件构造器
        LambdaQueryWrapper<Employee> qwery = new LambdaQueryWrapper<>();

        qwery.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加排序条件
        qwery.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo, qwery);

        return R.success(pageInfo);

    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        //设置时间
        employee.setUpdateTime(LocalDateTime.now());

        Long empID = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(empID);
        employeeService.updateById(employee);

        return R.success("员工信息更新成功");

    }


    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {//@PathVariable   路径变量
        Employee emp = employeeService.getById(id);
        if (emp != null) {
            return R.success(emp);
        }
        return R.error("没有查询到对应的员工信息");
    }

}
