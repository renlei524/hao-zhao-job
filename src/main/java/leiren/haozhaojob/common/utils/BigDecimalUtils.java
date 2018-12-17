package leiren.haozhaojob.common.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {
    /**
     * Object转BigDecimal类型
     * 数据库中数据单位分、 返回单位元
     * @param value 要转的object类型（单位分）
     * @return 转成的BigDecimal类型数据（单位元）
     */
    public static BigDecimal ObjectToBigDecimal(Object value) {
        BigDecimal bigDecimal = null;
        //分转换元参数
        BigDecimal divisor = new BigDecimal(100);
        if (value != null && value != "") {
            try {
                bigDecimal = (new BigDecimal(String.valueOf(value))).divide(divisor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bigDecimal;
    }
}
