package com.scxxwb.net.admin.common.utils;

/**
 * Redis所有Keys
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

    public static String getShiroSessionKey(String key){
        return "sessionid:" + key;
    }
}
