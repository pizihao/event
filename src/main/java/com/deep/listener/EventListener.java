package com.deep.listener;

import com.deep.event.Event;

import java.lang.annotation.*;

/**
 * 添加监听器的注解，可以作用于方法上，其执行方法的参数只能是其监听的事件类型，否则会抛出异常
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventListener {

    /**
     * 需要监听的事件
     */
    Class<? extends Event> value();

    /**
     * 是否异步执行
     */
    boolean isAsync() default false;

    /**
     * 监听器优先度，order越大。优先度越低
     */
    int order() default 0;


}
