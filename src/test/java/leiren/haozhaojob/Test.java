package leiren.haozhaojob;

import leiren.haozhaojob.common.utils.MD5Utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.UUID;

public class Test {
    public static void main(String[] arg) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));

        DecimalFormat df = new DecimalFormat(".00");
        Double b = new BigDecimal((float)(5000 / 100.0)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(df.format(b));
        System.out.println(MD5Utils.MD5("123456"));
    }
}
