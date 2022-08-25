package com.deep.event;

/**
 * 基于主题实现的事件发布<br>
 *
 * @author Create by liuwenhao on 2022/8/25 16:56
 */
public interface TopicEventPublisher extends EventPublisher {

    /**
     * 发布事件<br>
     * 通过主题匹配具体的监听器
     *
     * @param topic 主题
     */
    void publish(Object topic);
}