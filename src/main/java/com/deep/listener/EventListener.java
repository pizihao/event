package com.deep.listener;

import com.deep.event.Event;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
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
     * 监听器优先度，order越大。优先度越低，最小为0，小于0的视为无效事件
     */
    int order() default 0;


}
