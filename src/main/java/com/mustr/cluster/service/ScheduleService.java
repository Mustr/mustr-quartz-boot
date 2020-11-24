package com.mustr.cluster.service;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ScheduleService {

    @Autowired
    private Scheduler scheduler;
    
    /**
     * 停止job
     * @param jobKey
     * @return
     */
    public boolean pauseJob(JobKey jobKey) {
        try {
            //scheduler.pauseTrigger(new TriggerKey(jobKey.getName(), jobKey.getGroup()));
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 恢复启动job
     * @param jobKey
     * @return
     */
    public boolean resumeJob(JobKey jobKey) {
        try {
            //scheduler.resumeTrigger(new TriggerKey(jobKey.getName(), jobKey.getGroup()));
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @param jobKey
     * @return
     */
    public boolean update(JobKey jobKey, String cron) {
      //更新调度器中的trigger
        try {
            TriggerKey triggerKey = new TriggerKey(jobKey.getName(), jobKey.getGroup());
            Trigger trigger = scheduler.getTrigger(triggerKey);
            
            Trigger newTrigger = TriggerBuilder.newTrigger()
                .forJob(trigger.getJobKey())
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing())
                .withDescription(trigger.getDescription())
                .build();
            scheduler.rescheduleJob(triggerKey, newTrigger);
            //scheduler.scheduleJob(jobDetail, newTrigger);
            //scheduler.re
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
