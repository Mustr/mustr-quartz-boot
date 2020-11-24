package com.mustr;

import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;

import com.mustr.cluster.service.ScheduleService;

public class TestScheduleService extends MustrQuartzBootApplicationTests{

    @Autowired
    private ScheduleService scheduleService;
    
    private JobKey jobKey = new JobKey("hello1", "helloGroup");
    
    @Test
    public void test1() {
        scheduleService.pauseJob(jobKey);
    }
    
    
    @Test
    public void test2() {
        scheduleService.resumeJob(jobKey);
    }
    
    @Test
    public void test3() {
        scheduleService.update(jobKey, "0/5 * * * * ?");
    }
}
