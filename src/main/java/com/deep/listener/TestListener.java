package com.deep.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * <h2></h2>
 *
 * @author Create by liuwenhao on 2022/7/1 10:07
 */
@Component
public class TestListener {

    @EventListener(ApplicationStartedEvent.class)
    public ApplicationStartedEvent onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("=====事件监听成功======");
        return event;
    }
}