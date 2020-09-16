package com.sai.oabs.employee.repository;

import com.sai.oabs.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByCode(String code);

}
