package com.mustr.cluster.config;

import java.io.Serializable;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.util.MethodInvoker;

public class MustrCommonJob implements Job, Serializable{
    private static final long serialVersionUID = 8651275978441122356L;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Object targetClass = context.getMergedJobDataMap().get("targetClass");
        String targetMethod = context.getMergedJobDataMap().getString("targetMethod");
        String param = context.getMergedJobDataMap().getString("arguments");
        
        //前置处理
        // do ....
        
        try {
            MethodInvoker methodInvoker = new MethodInvoker();
            methodInvoker.setTargetObject(targetClass);
            methodInvoker.setTargetMethod(targetMethod);
            if (param != null && !"".equals(param)) {
                String[] params = param.split(",");
                Object[] temp = new Object[params.length];
                for (int i = 0; i < params.length; i++) {
                    temp[i] = params[i];
                }
                methodInvoker.setArguments(temp);
            }
            methodInvoker.prepare();
            methodInvoker.invoke();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            
            //后置处理
            // do  ... 如记录日志
        }
    }

}
