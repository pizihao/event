package com.deep.event;

import java.util.Collection;

/**
 * 统一上下文接口<br>
 * 事件和监听器的绑定，解绑，筛选监听器，处理事件等操作。<br>
 *
 * @author Create by liuwenhao on 2022/9/20 10:02
 */
public interface EventContext<T> {

    /**
     * 提取上下文的唯一标识
     *
     * @return name
     */
    String name();

    /**
     * 修改事件的监听模式
     *
     * @param t               事件类型
     * @param listenerPattern 事件监听模式
     */
    void setListenerPattern(T t, ListenerPattern listenerPattern);

    /**
     * 为一个事件绑定监听器
     *
     * @param t        事件类型
     * @param listener 监听器
     * @param <E>      事件类型
     * @param <R>      监听器返回类型
     */
    <E, R> void bind(T t, Listener<E, R> listener);

    /**
     * 为一个事件解绑监听器
     *
     * @param t        事件类型
     * @param listener 监听器
     * @param <E>      事件类型
     * @param <R>      监听器返回类型
     */
    <E, R> void unbind(T t, Listener<E, R> listener);

    /**
     * 为一个事件解绑所有监听器
     *
     * @param t 事件类型
     */
    void unbindAll(T t);

    /**
     * 清空当前的上下文
     */
    void clear();

    /**
     * 获取与一个事件绑定所有的监听器
     *
     * @param t   事件类型
     * @param <E> 事件类型
     * @return 监听器集合
     */
    <E> Collection<Listener<E, Object>> getListeners(T t);

}
