package com.sky.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.vo.EmployeeLoginVO;

public interface EmployeeService {
    
    EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO);
    
    void save(EmployeeDTO employeeDTO);
    
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
    
    void updateStatus(Integer status, Long id);
    
    Employee getById(Long id);
    
    void update(EmployeeDTO employeeDTO);
    
    void deleteById(Long id);
}
