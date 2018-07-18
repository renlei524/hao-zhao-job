package com.scxxwb.net.admin.modules.sys.controller;

import com.scxxwb.net.admin.common.kafka.KafkaProducer;
import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller公共组件
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected KafkaProducer kafkaProducer;
	
	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Integer getUserId() {
		return getUser().getUserId();
	}

	protected Integer getDeptId() {
		return getUser().getDeptId();
	}
}
