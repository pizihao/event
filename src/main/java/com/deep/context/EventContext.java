package com.deep.context;

import com.deep.event.Event;
import com.deep.event.FakeEvent;
import com.deep.listener.Listener;

import java.util.List;

/**
 * 事件上下文
 * 同一个事件或者监听器可以同时存在于不同的上下文中
 *
 * @author Create by liuwenhao on 2022/6/29 10:53
 */
public class EventContext {

    /**
     * 上下文名称
     */
    String name;

    /**
     * 存放所有的事件和监听器
     */
    EventBindMap eventBindMap = new EventBindMap();

    public EventContext(String name) {
        this.name = name;
    }

    /**
     * 发布事件
     *
     * @param o 需要发布的事件对象
     */
    public void publishEvent(Object o) {
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
        List<Listener<Event,Event>> listeners = getListeners(event.getClass());

        // 监听器中存在的是

    }

    /**
     * 为一个事件添加监听器
     *
     * @param eventCls 事件类型
     * @param listener 监听器
     */
    public void add(Class<? extends Event> eventCls, Listener<Event, Event> listener) {
        eventBindMap.add(eventCls, listener);
    }

    /**
     * 为一个事件删除监听器
     *
     * @param eventCls 事件类型
     * @param listener 监听器
     */
    public void remove(Class<? extends Event> eventCls, Listener<Event,Event> listener) {
        eventBindMap.remove(eventCls, listener);
    }

    /**
     * 获取所有的监听器
     * 获取后的监听器被改变后不影响环境中的监听器
     */
    public List<Listener<Event,Event>> getListeners() {
        return eventBindMap.getListeners();
    }

    /**
     * 获取一个事件所有的监听器
     */
    public List<Listener<Event,Event>> get(Class<? extends Event> eventCls) {
        return eventBindMap.get(eventCls);
    }

    /**
     * 获取一个事件所有的监听器
     * 获取后的监听器被改变后不影响环境中的监听器
     *
     * @param eventCls 事件类型
     */
    public List<Listener<Event,Event>> getListeners(Class<? extends Event> eventCls) {
        return eventBindMap.getListeners(eventCls);
    }

}