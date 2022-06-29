package com.deep.listener;

import com.deep.event.Event;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <h2>监听器代理</h2>
 *
 * @author Create by liuwenhao on 2022/6/29 10:09
 */
public class ListenerProxy<E extends Event, T extends Event> implements Listener<E, T>, InvocationHandler {

    /**
     * 监听器
     */
    Listener<E, T> listener;


    public ListenerProxy(Listener<E, T> listener) {
        this.listener = listener;
    }

    public Listener<E, T> getListener() {
        return listener;
    }

    public void setListener(Listener<E, T> listener) {
        this.listener = listener;
    }

    @Override
    public E execEvent(T t) {
        return getListener().execEvent(t);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(args);
    }
}