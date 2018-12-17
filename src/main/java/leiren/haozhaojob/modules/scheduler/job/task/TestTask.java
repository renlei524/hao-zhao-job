/*
package com.scxxwb.net.admin.modules.scheduler.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

*/
/**
 * 重点自媒体，10分钟 作业
 * Created by leiren on 2018/1/5.
 *//*

public class TestTask implements SimpleJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTask.class);

    */
/*@Autowired
    private Accounts_focusService accounts_focusService;*//*


    @Override
    public void execute(ShardingContext shardingContext) {
        LOGGER.info("Accounts_focusJobScheduler has started, time is : {}", LocalDateTime.now());

        */
/*try {
            List<Accounts_focus> accounts_focusList = accounts_focusService.findAll();
            accounts_focusService.updateTypePerAndStylesAndHotDegree(accounts_focusList);
        } catch (Exception e) {
            RestApiEmailUtil.sendEmail("Accounts_focusTask Exception Message: " + e.getMessage());
        }*//*


        LOGGER.info("Accounts_focusJobScheduler is ending, time is : {}", LocalDateTime.now());
    }
}
*/
