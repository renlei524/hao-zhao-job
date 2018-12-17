/*
package com.scxxwb.net.admin.common.utils;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import DynamicDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

*/
/**
 * ElasticJob工具
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.08.22
 *//*

@Component
public class ElasticJobHandler {
    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Resource
    private JobEventConfiguration jobEventConfiguration;

    */
/**
     * @param jobName            任务名
     * @param jobClass           任务class
     * @param cron               表达式
     * @param shardingTotalCount 分片数
     * @param id                 job参数
     * @param parameters         分片参数
     * @return
     *//*

    private static LiteJobConfiguration.Builder simpleJobConfigBuilder(String jobName,
                                                                       Class<? extends SimpleJob> jobClass,
                                                                       int shardingTotalCount,
                                                                       String cron,
                                                                       String id,
                                                                       String parameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration
                        .newBuilder(jobName, cron, shardingTotalCount)
                        .shardingItemParameters(parameters)
                        .jobParameter(id).
                        build(),
                jobClass.getCanonicalName()));
    }

    */
/**
     * 添加一个定时任务
     *
     * @param jobName            任务名
     * @param jobClass           任务class
     * @param cron               表达式
     * @param shardingTotalCount 分片数
     * @param id                 job参数
     * @param parameters         分片参数
     * @param elasticJobListener job监听器
     *//*

    public void addJob(String jobName,
                       final Class<? extends SimpleJob> jobClass,
                       String cron,
                       Integer shardingTotalCount,
                       String id,
                       String parameters,
                       ElasticJobListener elasticJobListener)
            throws IllegalAccessException, InstantiationException {

        LiteJobConfiguration jobConfig = simpleJobConfigBuilder(jobName, jobClass, shardingTotalCount, cron, id,parameters)
                .overwrite(true).build();
        new SpringJobScheduler(jobClass.newInstance(), regCenter, jobConfig, jobEventConfiguration, elasticJobListener).init();
    }
}
*/
