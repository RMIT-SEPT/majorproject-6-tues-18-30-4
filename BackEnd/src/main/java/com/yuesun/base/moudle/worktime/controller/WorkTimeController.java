package com.yuesun.base.moudle.worktime.controller;

import com.yuesun.base.moudle.worktime.entity.WorkTime;
import com.yuesun.base.moudle.worktime.service.WorkTimeService;
import com.yuesun.core.controller.AdminController;
import com.yuesun.core.entity.HttpResult;
import com.yuesun.core.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@RestController
@RequestMapping("/worktime")
public class WorkTimeController extends AdminController {

    @Autowired
    private WorkTimeService workTimeService;

    @PostMapping("")
    public HttpResult save(WorkTime workTime){
        workTime.setTime(DateUtils.strToDate(workTime.getTimeStr()));
        if (workTimeService.findByDateAndEmployee(workTime.getTime(),workTime.getEmployeeId()) != null){
            return returnSystemError("workTime already exist");
        }
        workTimeService.add(workTime);
        return returnSuccess("save success");
    }

    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable Long id) {
        workTimeService.deleteById(id);
        return returnSuccess("delete success");
    }

    @GetMapping("")
    public Object list(ServletRequest request) {
        PageRequest pageRequest = buildDataPage(request);
        return getPage(workTimeService.findByPage(pageRequest));
    }
}
