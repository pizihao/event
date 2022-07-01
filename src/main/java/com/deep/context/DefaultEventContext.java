package com.deep.context;

import com.deep.event.Event;
import com.deep.event.FakeEvent;
import com.deep.listener.Listener;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * <h2>默认上下文实现</h2>
 *
 * @author Create by liuwenhao on 2022/7/1 14:21
 */
public class DefaultEventContext implements EventContext {

    /**
     * 上下文名称
     */
    String name;

    /**
     * 存放所有的事件和监听器
     */
    final EventBindMap eventBindMap = new EventBindMap();

    public DefaultEventContext(String name) {
        this.name = name;
    }

    /**
     * 发布事件
     *
     * @param o 需要发布的事件对象
     */
    @Override
    public void publish(Object o) {
        if (o == null) {
            return;
        }
        Event event;
        // o必须在前面
        if (o.getClass().isAssignableFrom(Event.class)) {
            event = (Event) o;
        } else {
            event = new FakeEvent<>(this, o);
        }
        // 得到所有的监听器
        Set<Listener> listeners = getListeners(event.getClass());
        if (!listeners.isEmpty()) {
            newProxy(listeners, event);
        }
    }

    void newProxy(Set<Listener> listeners, Event event) {
        DefaultEventContextProxy contextProxy = new DefaultEventContextProxy(name, listeners, event);
        try {
            contextProxy.doInvoke();
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    /**
     * 为一个事件添加监听器
     *
     * @param type     事件类型
     * @param listener 监听器
     */
    public void addListener(Type type, Listener listener) {
        synchronized (eventBindMap) {
            Set<Listener> listeners = eventBindMap.map.get(type);
            if (listener == null) {
                listeners = new HashSet<>();
            }
            listeners.add(listener);
        }
    }

    /**
     * 为一个事件删除监听器
     *
     * @param type     事件类型
     * @param listener 监听器
     */
    public void remove(Type type, Listener listener) {
        synchronized (eventBindMap) {
            Set<Listener> listeners = eventBindMap.map.get(type);
            listeners.remove(listener);
        }
    }

    /**
     * 获取所有的监听器
     */
    public Set<Listener> getListeners() {
        return eventBindMap.getListeners();
    }

    /**
     * 获取一个事件所有的监听器
     */
    public Set<Listener> getListeners(Type type) {
        return eventBindMap.getListeners(type);
    }


    /**
     * 监听器和事件绑定映射
     * 参照{@link java.beans.ChangeListenerMap}实现
     * 使用ConcurrentHashMap代替原来的synchronized
     *
     * @author Create by liuwenhao on 2022/6/29 11:16
     */
    static class EventBindMap {

        /**
         * 存储可以直接声明的监听器
         */
        Map<Type, Set<Listener>> map = new ConcurrentHashMap<>();

        public final Set<Listener> getListeners() {
            return map.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        }

        public final Set<Listener> getListeners(Type type) {
            if (type == null) {
                return Collections.emptySet();
            }
            return new HashSet<>(map.get(type));
        }

    }
}