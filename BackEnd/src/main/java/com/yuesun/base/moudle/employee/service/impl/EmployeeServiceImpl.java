package com.yuesun.base.moudle.employee.service.impl;

import com.yuesun.base.moudle.employee.entity.Employee;
import com.yuesun.base.moudle.employee.repository.EmployeeRepository;
import com.yuesun.base.moudle.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("employeeService")
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = false)
    public void add(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> findByPage(PageRequest pageRequest) {
        PageRequest request = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize());
        return employeeRepository.findAll(request);
    }

    @Override
    public Employee findByCode(String code) {
        return employeeRepository.findByCode(code);
    }
}
