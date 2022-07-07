package com.deep.sparrow.context;

import com.deep.sparrow.listener.Listener;
import com.deep.sparrow.publisher.EventPublisher;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 事件上下文
 * 同一个事件或者监听器可以同时存在于不同的上下文中
 *
 * @author Create by liuwenhao on 2022/6/29 10:53
 */
public interface EventContext extends EventPublisher {


    /**
     * 提取上下文的唯一标识
     */
    String name();

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
     * 清空当前上下文
     */
    void clean();

    /**
     * 获取所有的监听器，
     * 得到的监听器仅用于操作，其无法获知监听的具体事件
     */
    List<Listener> getListeners();

    /**
     * 获取一个事件所有的监听器
     */
    List<Listener> getListeners(Type type);

}