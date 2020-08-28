package com.yuesun.base.moudle.employee.repository;

import com.yuesun.base.moudle.employee.entity.Employee;
import com.yuesun.core.repository.BaseRepository;

public interface EmployeeRepository extends BaseRepository<Employee, Long> {

    Employee findByCode(String code);

}
