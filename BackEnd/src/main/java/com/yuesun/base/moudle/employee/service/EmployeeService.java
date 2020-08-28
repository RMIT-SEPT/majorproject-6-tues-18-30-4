package com.yuesun.base.moudle.employee.service;

import com.yuesun.base.moudle.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface EmployeeService {

    public void add(Employee employee);

    public void deleteById(Long id);

    public Page<Employee> findByPage(PageRequest pageRequest);

    public Employee findByCode(String code);

}
