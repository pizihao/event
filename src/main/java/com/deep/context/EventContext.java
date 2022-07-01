package com.deep.context;

import com.deep.listener.Listener;
import com.deep.publish.EventPublisher;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * 事件上下文
 * 同一个事件或者监听器可以同时存在于不同的上下文中
 *
 * @author Create by liuwenhao on 2022/6/29 10:53
 */
public interface EventContext extends EventPublisher {

    /**
     * 为一个事件添加监听器
     *
     * @param type     事件类型
     * @param listener 监听器
     */
    void addListener(Type type, Listener listener);

    /**
     * 为一个事件删除监听器
     *
     * @param type     事件类型
     * @param listener 监听器
     */
    void remove(Type type, Listener listener);

    /**
     * 获取所有的监听器
     */
    Set<Listener> getListeners();

    /**
     * 获取一个事件所有的监听器
     */
    Set<Listener> getListeners(Type type);

}