package leiren.haozhaojob.common.annotation;

import java.lang.annotation.*;

/**
 * Excel注解
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.08.15
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelField {
    /** Excel里列名*/
    String columnName();

    /** 排序号*/
    int sort() default 0;
}
