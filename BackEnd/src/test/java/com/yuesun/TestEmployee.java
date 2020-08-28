package com.yuesun;

import com.yuesun.base.moudle.employee.entity.Employee;
import com.yuesun.base.moudle.employee.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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
        employeeService.add(employee);
    }

}
