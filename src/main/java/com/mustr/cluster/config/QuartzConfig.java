package com.mustr.cluster.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class QuartzConfig {

    /**
     * 配置任务调度器
     * 使用项目数据源作为quartz数据源
     * @param jobFactory 自定义配置任务工厂
     * @param dataSource 数据源实例
     * @return
     * @throws Exception
     */
    @Bean(destroyMethod = "destroy")
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource,
        ObjectProvider<PlatformTransactionManager> transactionManager) throws Exception {
        
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // 设置调度器自动运行
        schedulerFactoryBean.setAutoStartup(false);
        // 设置数据源，使用与项目统一数据源
        schedulerFactoryBean.setDataSource(dataSource);

        PlatformTransactionManager txManager = transactionManager.getIfUnique();
        if (txManager != null) {
            schedulerFactoryBean.setTransactionManager(txManager);
        }
        // 设置上下文spring bean name
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        // 设置配置文件位置
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
        return schedulerFactoryBean;
    }
    
    @Bean
    public MScheduleBeanPostProcessor mScheduleBeanPostProcessor() {
        return new MScheduleBeanPostProcessor();
    }
}
