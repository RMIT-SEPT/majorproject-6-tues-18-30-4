package com.yuesun.base.moudle.worktime.repository;

import com.yuesun.base.moudle.employee.entity.Employee;
import com.yuesun.base.moudle.worktime.entity.WorkTime;
import com.yuesun.core.repository.BaseRepository;

import java.util.Date;
import java.util.List;

public interface WorkTimeRepository extends BaseRepository<WorkTime, Long> {

    List<WorkTime> findByTimeAndEmployeeId(Date time,Long employeeId);


}
