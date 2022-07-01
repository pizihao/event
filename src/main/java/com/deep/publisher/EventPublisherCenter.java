package com.deep.publisher;

import com.deep.context.EventContext;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2>事件发布中心</h2>
 * 可以创建多个不同EventPublisherCenter
 *
 * @author Create by liuwenhao on 2022/7/1 16:57
 */
public class EventPublisherCenter {

    /**
     * 所有的上下文
     */
    Map<String, EventContext> map = new HashMap<>();

    public EventPublisherCenter(Map<String, EventContext> map) {
        this.map = map;
    }

    public EventPublisherCenter() {
    }



}