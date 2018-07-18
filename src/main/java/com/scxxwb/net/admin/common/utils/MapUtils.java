package com.scxxwb.net.admin.common.utils;

import java.util.HashMap;


/**
 * Map工具类
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
