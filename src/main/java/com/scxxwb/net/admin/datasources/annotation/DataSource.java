package com.scxxwb.net.admin.datasources.annotation;

import java.lang.annotation.*;

/**
 * 多数据源注解
 *
 * @author leiren
 * @email renlei@scxxwb.com
 * @date 2018.05.28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}
