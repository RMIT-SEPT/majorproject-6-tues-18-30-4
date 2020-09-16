package com.sai.oabs;

import com.sai.oabs.employee.entity.Employee;
import com.sai.oabs.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestEmployee {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testSave(){
        Employee employee = new Employee();
        employee.setCode("001");
        employee.setName("sai");
        employee.setPhone("1234567899-1");
        employee.setAddress("New York");
        employeeService.save(employee);
    }

    @Test
    public void testDelete(){
        employeeService.delete(1L);
    }

    @Test
    public void testFindByCode(){
        Employee employee = employeeService.findByCode("001");
        System.out.println(employee);
    }

    @Test
    public void testFindById(){
        Employee employee = employeeService.findById(1L);
        System.out.println(employee);
    }

}
