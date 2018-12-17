/*
package com.scxxwb.net.admin.modules.scheduler.scheduler;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import EJobCronEnum;
import com.scxxwb.net.admin.modules.scheduler.job.listenter.TestListener;
import com.scxxwb.net.admin.modules.scheduler.job.task.TestTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

*/
/**
 * 重点自媒体，10分钟 调度器
 * Created by leiren on 2018/1/5.
 *//*

@Configuration
public class TestScheduler {
    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Resource
    private JobEventConfiguration jobEventConfiguration;

    @Bean
    public TestTask testTask() {
        return new TestTask();
    }

    @Bean(initMethod = "init")
    public JobScheduler test1Scheduler(final TestTask testTask, @Value("3") final int shardingTotalCount){
        final String cron = EJobCronEnum.ACCOUNTS_FOCUS_INTERVAL_TEN_MINUTES.getCron();
        return new SpringJobScheduler(testTask, regCenter, createSimpleJobConfiguration(testTask.getClass(), cron, shardingTotalCount), jobEventConfiguration, new TestListener());
    }

    // Simple类型作业配置
    private LiteJobConfiguration createSimpleJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount) {
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount).build();
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, jobClass.getCanonicalName());
        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).build();

        return simpleJobRootConfig;
    }
}
*/
