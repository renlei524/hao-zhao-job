package com.scxxwb.net.admin;

import com.scxxwb.net.admin.common.utils.MD5Utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Test {
    public static void main(String[] arg) {
        DecimalFormat df = new DecimalFormat(".00");
        Double b = new BigDecimal((float)(5000 / 100.0)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(df.format(b));
        System.out.println(MD5Utils.MD5("123456"));
    }
}
