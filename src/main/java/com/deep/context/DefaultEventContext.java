package com.deep.context;

import com.deep.event.Event;
import com.deep.event.FakeEvent;
import com.deep.exception.EventException;
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
        // o必须在后面
        if (Event.class.isAssignableFrom(o.getClass())) {
            event = (Event) o;
        } else {
            event = new FakeEvent<>(this, o);
        }
        // 得到所有的监听器
        Set<Listener> listeners = getListeners(o.getClass());
        if (!listeners.isEmpty()) {
            newProxy(listeners, event);
        }
    }

    /**
     * 通过代理类来实现事件的顺延发布，当监听器执行结果为null或当前事件本身时不再进行顺延，
     * 即执行结果必须是完完全全的本身，其对比方式为 {@link Objects#equals(Object, Object)}，
     * 故可以通过重写equals来影响判断结果。
     * 理论上来讲事件可以无限顺延下去，直到栈溢出。
     *
     * @param listeners 监听器
     * @param event     事件对象
     */
    void newProxy(Set<Listener> listeners, Event event) {
        DefaultEventContextProxy contextProxy = new DefaultEventContextProxy(name, listeners, event, eventBindMap);
        try {
            contextProxy.doInvoke();
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new EventException(e);
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultEventContext that = (DefaultEventContext) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
            if (listeners == null) {
                listeners = new HashSet<>();
            }
            listeners.add(listener);
            eventBindMap.map.put(type, listeners);
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
            if (Objects.nonNull(listeners)) {
                listeners.remove(listener);
            }
        }
    }

    @Override
    public void clean() {
        synchronized (eventBindMap) {
            eventBindMap.map.clear();
        }
    }

    /**
     * 获取所有的监听器
     */
    public List<Listener> getListeners() {
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
         * 存储所有的监听器，如果采用注解的方式直接在方法上声明的监听器则会通过动态代理为其生成一个Listener对象。
         * 这是为了在发布事件阶段上下文拿到的监听器一定是Listener或Listener的实现类
         */
        Map<Type, Set<Listener>> map = new ConcurrentHashMap<>();

        public final List<Listener> getListeners() {
            return map.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        }

        public final Set<Listener> getListeners(Type type) {
            if (Objects.isNull(type)) {
                return Collections.emptySet();
            }
            Set<Listener> listeners = map.get(type);
            if (Objects.isNull(listeners)) {
                return Collections.emptySet();
            }
            return new HashSet<>(listeners);
        }

    }
}