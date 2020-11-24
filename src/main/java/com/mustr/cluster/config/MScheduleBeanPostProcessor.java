package com.mustr.cluster.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.mustr.cluster.annotation.MSchedule;
import com.mustr.cluster.annotation.MScheduleClass;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MScheduleBeanPostProcessor implements BeanPostProcessor, ApplicationListener<ContextRefreshedEvent>, DisposableBean {

    @Autowired
    private Scheduler scheduler;
    
    private List<MustrTask> tasks = new ArrayList<>();
    
    
    @Override
    public void destroy() throws Exception {
        scheduler.shutdown();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("all scheduler tasks total {}", tasks.size());
        
        try {
            //先把原来的都删除
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyGroup());
            scheduler.deleteJobs(new ArrayList<>(jobKeys));
        } catch (SchedulerException e1) {
            e1.printStackTrace();
        }
        
        //重新添加新的
        tasks.forEach(task -> {
            try {
                scheduler.scheduleJob(task.getJobDetail(), task.getTrigger());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        });
        
        try {
            scheduler.start(); //启动调度器
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        MScheduleClass msClass = bean.getClass().getAnnotation(MScheduleClass.class);
        if (msClass == null) {
            return bean;
        }
        
        String group = bean.getClass().getSimpleName();
        Method[] methods = bean.getClass().getDeclaredMethods();
        if (methods == null) {
            return bean;
        }
        
        
        for (Method method : methods) {
            MSchedule mSchedule = method.getAnnotation(MSchedule.class);
            if (mSchedule == null) {
                continue;
            }
            hanlderSchedule(group, mSchedule, method, bean);
        }
        
        return bean;
    }

    private void hanlderSchedule(String group, MSchedule mSchedule, Method method, Object bean) {
        String jobName = method.getName();
        
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("targetClass", bean);
        jobDataMap.put("targetMethod", method.getName());
        jobDataMap.put("arguments", mSchedule.param());
        
        JobDetail jobDetail = JobBuilder.newJob(MustrCommonJob.class)
            .setJobData(jobDataMap)
            .withIdentity(new JobKey(jobName, group))
            .withDescription(mSchedule.desc())
            .storeDurably()
            .build();
        
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(new TriggerKey(jobName, group))
            .withDescription(mSchedule.desc())
            .withSchedule(CronScheduleBuilder.cronSchedule(mSchedule.corn()).withMisfireHandlingInstructionDoNothing())
            .forJob(jobDetail)
            .build();
        
        tasks.add(new MustrTask(jobDetail, trigger));
    }
}
