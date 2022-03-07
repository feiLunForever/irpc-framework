package org.idea.irpc.framework.spring.starter.common;

import java.lang.annotation.*;

/**
 * @Author linhao
 * @Date created in 7:28 下午 2022/3/7
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IRpcReference {
}
