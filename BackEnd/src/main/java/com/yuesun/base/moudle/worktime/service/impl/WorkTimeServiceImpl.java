package com.yuesun.base.moudle.worktime.service.impl;


import com.yuesun.base.moudle.worktime.entity.WorkTime;
import com.yuesun.base.moudle.worktime.repository.WorkTimeRepository;
import com.yuesun.base.moudle.worktime.service.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service("workTimeService")
@Transactional(readOnly = true)
public class WorkTimeServiceImpl implements WorkTimeService {

    @Autowired
    private WorkTimeRepository workTimeRepository;

    @Override
    @Transactional(readOnly = false)
    public void add(WorkTime WorkTime) {
        workTimeRepository.save(WorkTime);
    }

    @Override
    public void deleteById(Long id) {
        workTimeRepository.deleteById(id);
    }

    @Override
    public Page<WorkTime> findByPage(PageRequest pageRequest) {
        PageRequest request = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize());
        return workTimeRepository.findAll(request);
    }

    @Override
    public WorkTime findByDateAndEmployee(Date time, Long employeeId) {
        List<WorkTime> workTimes = workTimeRepository.findByTimeAndEmployeeId(time, employeeId);
        if (workTimes.isEmpty()){
            return null;
        }else{
            return workTimes.get(0);
        }

    }
}
