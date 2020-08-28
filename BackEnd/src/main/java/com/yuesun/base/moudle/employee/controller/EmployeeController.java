package com.yuesun.base.moudle.employee.controller;

import com.yuesun.base.moudle.employee.entity.Employee;
import com.yuesun.base.moudle.employee.service.EmployeeService;
import com.yuesun.core.controller.AdminController;
import com.yuesun.core.entity.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@RestController
@RequestMapping("/employee")
public class EmployeeController extends AdminController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("")
    public HttpResult save(Employee employee){
        if (employeeService.findByCode(employee.getCode()) != null){
            return returnSystemError("code 【"+employee.getCode()+"】 already exist");
        }
        employeeService.add(employee);
        return returnSuccess("save success");
    }

    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return returnSuccess("delete success");
    }

    @GetMapping("")
    public Object list(ServletRequest request) {
        PageRequest pageRequest = buildDataPage(request);
        return getPage(employeeService.findByPage(pageRequest));
    }
}
