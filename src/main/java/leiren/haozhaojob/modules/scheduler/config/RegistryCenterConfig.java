/*
package com.scxxwb.net.admin.modules.scheduler.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

*/
/**
 * 作业注册中心 配置
 * Created by leiren on 2018/1/5.
 *//*

@Configuration
@ConditionalOnExpression("'${scxxwb.elasticJob.regCenter.serverList}'.length() > 0")
public class RegistryCenterConfig {

   @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(@Value("${scxxwb.elasticJob.regCenter.serverList}") final String serverList, @Value("${scxxwb.elasticJob.regCenter.namespace}") final String namespace) {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(serverList, namespace);
        zookeeperConfiguration.setBaseSleepTimeMilliseconds(1000);
        zookeeperConfiguration.setMaxSleepTimeMilliseconds(3000);
        zookeeperConfiguration.setMaxRetries(3);
        zookeeperConfiguration.setSessionTimeoutMilliseconds(60000);
        zookeeperConfiguration.setConnectionTimeoutMilliseconds(15000);

        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }
}
*/
