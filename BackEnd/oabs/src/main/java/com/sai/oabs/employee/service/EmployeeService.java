package com.sai.oabs.employee.service;

import com.sai.oabs.employee.entity.Employee;
import com.sai.oabs.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void save(Employee employee){
        employeeRepository.save(employee);
    }

    public void delete(Long id){
        employeeRepository.deleteById(id);
    }

    public Employee findById(Long id){
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee findByCode(String code){
        return employeeRepository.findByCode(code);
    }
}
