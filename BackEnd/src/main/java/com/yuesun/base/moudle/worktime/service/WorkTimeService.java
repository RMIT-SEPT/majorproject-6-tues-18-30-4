package com.yuesun.base.moudle.worktime.service;

import com.yuesun.base.moudle.worktime.entity.WorkTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

public interface WorkTimeService {

    public void add(WorkTime WorkTime);

    public void deleteById(Long id);

    public Page<WorkTime> findByPage(PageRequest pageRequest);

    WorkTime findByDateAndEmployee(Date time,Long employeeId);
}
