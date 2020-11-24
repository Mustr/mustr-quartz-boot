package com.mustr.simple;

import java.time.LocalDateTime;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(context);
        System.out.println("hello simple quartz。。。");
        System.out.println(LocalDateTime.now());
    }

}
