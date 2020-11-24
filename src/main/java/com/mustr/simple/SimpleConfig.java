package com.mustr.simple;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

//@Component
public class SimpleConfig implements InitializingBean{

    private Scheduler scheduler;
    
    
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化simple 任务执行....");
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
            .usingJobData("k1", "v1")
            .usingJobData("k2", 2)
            .withIdentity(new JobKey("simplejob", "simple"))
            .build();
        
        CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule("0/30 * * * * ?");
        
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(new TriggerKey("simpleTrigger", "simple"))
            .withSchedule(schedBuilder)
            .build();
        
        scheduler.scheduleJob(jobDetail, trigger);
        
        
        scheduler.startDelayed(5);
    }
    
}
