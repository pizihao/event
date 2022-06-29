package com.deep.context;

import com.deep.event.Event;
import com.deep.listener.Listener;
import com.deep.listener.ListenerProxy;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 监听器和事件绑定映射
 * 参照{@link java.beans.ChangeListenerMap}实现
 * 使用ConcurrentHashMap代替原来的synchronized
 *
 * @author Create by liuwenhao on 2022/6/29 11:16
 */
public class EventBindMap {

    /**
     * 存储可以直接声明的监听器
     */
    Map<Class<? extends Event>, List<Listener<Event, Event>>> map = new ConcurrentHashMap<>();

    Map<Class<? extends Event>, List<Method>> mapMethod = new ConcurrentHashMap<>();

    public Listener<Event, Event> newProxy(Listener<Event, Event> t) {
        return new ListenerProxy<>(t);
    }

    public final void add(Class<? extends Event> cls, Listener<Event, Event> listener) {
        List<Listener<Event, Event>> ts = map.get(cls);
        ts.add(listener);
    }

    public final void remove(Class<? extends Event> cls, Listener<Event, Event> listener) {
        List<Listener<Event, Event>> ts = map.get(cls);
        ts.remove(listener);
    }

    public final List<Listener<Event, Event>> get(Class<? extends Event> cls) {
        return (map != null) ? map.get(cls) : null;
    }

    public final void set(Class<? extends Event> cls, List<Listener<Event, Event>> listeners) {
        map.put(cls, listeners);
    }

    public final List<Listener<Event, Event>> getListeners() {
        List<Listener<Event, Event>> ts = map.values().stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        return new ArrayList<>(ts);
    }

    public final List<Listener<Event, Event>> getListeners(Class<? extends Event> cls) {
        return cls != null ? new ArrayList<>(map.get(cls)) : new ArrayList<>();
    }

}