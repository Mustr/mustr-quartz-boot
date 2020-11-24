package com.mustr.cluster.jobs;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.mustr.cluster.annotation.MSchedule;
import com.mustr.cluster.annotation.MScheduleClass;

@MScheduleClass(module = "系统", desc = "测试组")
public class HelloSchedule implements Serializable{
    private static final long serialVersionUID = 3619058186885794136L;

    /*@MSchedule(corn = "0/30 * * * * ?", desc = "打印hello world")
    public void hello() {
        System.out.println("hello mustr..... <<<:::>>>" + LocalDateTime.now());
    }*/
    
    @MSchedule(corn = "0/10 * * * * ?", desc = "打印hello world")
    public void hello1() {
        System.out.println("<<<<hello1 mustr..... <<<:::>>>" + LocalDateTime.now());
    }
}
