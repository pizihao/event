package com.deep.sparrow.publisher;

import com.deep.context.DefaultEventContext;
import com.deep.context.EventContext;
import com.deep.event.Event;
import com.deep.exception.EventException;
import com.deep.sparrow.listener.EventListener;
import com.deep.sparrow.listener.Listener;
import com.deep.sparrow.listener.OrderListenerDecorate;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * <h2>事件发布中心</h2>
 *
 * @author Create by liuwenhao on 2022/7/1 16:57
 */
class EventPublisherCenter {

    /**
     * 所有的上下文
     */
    Map<String, EventContext> map = new HashMap<>();

    public EventPublisherCenter(Map<String, EventContext> map) {
        this.map = map;
    }

    public EventPublisherCenter() {
    }

    /**
     * 添加指定的上下文，如果不存在则为null
     *
     * @param eventContext 上下文
     */
    public void addContext(EventContext eventContext) {
        if (Objects.isNull(eventContext)) {
            return;
        }
        map.put(eventContext.name(), eventContext);
    }

    /**
     * 获取指定的上下文，如果不存在则为null
     *
     * @param name 上下文标识
     * @return EventContext 上下文
     */
    public EventContext getContext(String name) {
        return map.get(name);
    }

    /**
     * 为一个上下文添加监听器
     * 如果上下文不存在则添加
     *
     * @param name     上下文名称
     * @param t        监听器对象
     * @param eventCls 被监听的事件类型
     * @param <T>      实现了{@link Listener}的监听器
     */
    public <T extends Listener> void addListener(String name, T t, Class<? extends Event> eventCls) {
        if (Objects.isNull(t)) {
            return;
        }
        EventContext eventContext = map.get(name);
        if (Objects.isNull(eventContext)) {
            eventContext = new DefaultEventContext(name);
        }
        map.put(name, eventContext);
        eventContext.addListener(eventCls, t);
    }

    /**
     * 为一个上下文添加监听器
     * 如果上下文不存在则添加
     *
     * @param name     上下文名称
     * @param listener 声明注解
     * @param o        监听器对象
     * @param method   监听器执行方法
     */
    public void addListener(String name, EventListener listener, Object o, Method method) {
        Listener l = newListenerProxy(o, method);
        OrderListenerDecorate listenerDecorate = new OrderListenerDecorate(listener.order(), l);
        addListener(name, listenerDecorate, listener.value());
    }

    /**
     * 为一个上下文添加监听器
     *
     * @param listener 声明注解
     * @param o        监听器对象
     * @param method   监听器执行方法
     */
    public void addListener(EventListener listener, Object o, Method method) {
        Listener l = newListenerProxy(o, method);
        addListener(l, listener.value());
    }

    /**
     * 生成一个Listener代理类
     *
     * @param o      代理的对象
     * @param method 需要被代理的方法
     * @return 监听器代理类
     */
    private Listener newListenerProxy(Object o, Method method) {
        Class<?>[] classes = {Listener.class};
        ClassLoader classLoader = this.getClass().getClassLoader();
        return (Listener) Proxy.newProxyInstance(
            classLoader,
            classes,
            (p, m, a) -> {
                if ("execEvent".equals(m.getName())) {
                    return method.invoke(o, a);
                }
                if (refuseGroup.contains(m.getName())) {
                    return m.invoke(o, a);
                }
                return null;
            }
        );
    }

    /**
     * 为所有上下文添加监听器
     * 注意：如果定义了太多的上下文会严重占用内存
     *
     * @param t        监听器对象
     * @param eventCls 被监听的事件类型
     * @param <T>      实现了{@link Listener}的监听器
     */
    public <T extends Listener> void addListener(T t, Class<? extends Event> eventCls) {
        if (Objects.isNull(t)) {
            return;
        }
        map.values().forEach(c -> c.addListener(eventCls, t));
    }

    /**
     * 在上下文中寻找对应的事件，并将指定的监听器删除
     *
     * @param name     上下文名称
     * @param t        监听器对象
     * @param eventCls 被监听的事件类型
     * @param <T>      实现了{@link Listener}的监听器
     */
    public <T extends Listener> void removeListener(String name, T t, Class<? extends Event> eventCls) {
        if (Objects.isNull(t)) {
            return;
        }
        EventContext eventContext = map.get(name);
        if (Objects.nonNull(eventContext)) {
            eventContext.remove(eventCls, t);
        }

    }

    /**
     * 在所有上下文中寻找对应的事件，并将指定的监听器删除
     *
     * @param t        监听器对象
     * @param eventCls 被监听的事件类型
     * @param <T>      实现了{@link Listener}的监听器
     */
    public <T extends Listener> void removeListener(T t, Class<? extends Event> eventCls) {
        if (Objects.isNull(t)) {
            return;
        }
        map.values().forEach(c -> c.remove(eventCls, t));

    }

    /**
     * 删除一个上下文
     *
     * @param name 上下文名称
     */
    public void removeContext(String name) {
        EventContext eventContext = map.get(name);
        eventContext.clean();
        map.remove(name);
    }

    /**
     * 在指定的上下文中发布一个事件
     *
     * @param name  上下文名称
     * @param event 事件
     */
    public void publish(String name, Object event) {
        if (Objects.isNull(name)) {
            return;
        }
        EventContext eventContext = map.get(name);
        if (Objects.isNull(eventContext)) {
            throw new EventException("未声明过该上下文  - " + name);
        }
        eventContext.publish(event);
    }


    static List<String> refuseGroup = new ArrayList<>();

    static {
        refuseGroup.add("hashCode");
        refuseGroup.add("equals");
        refuseGroup.add("clone");
        refuseGroup.add("toString");
        refuseGroup.add("finalize");
    }

}