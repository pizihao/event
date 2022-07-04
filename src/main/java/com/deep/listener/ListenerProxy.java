package com.deep.listener;

/**
 * 针对Listener的静态代理
 *
 * @author Create by liuwenhao on 2022/7/4 18:03
 */
public class ListenerProxy implements Listener {

    Listener listener;

    @Override
    public Object execEvent(Object t) {
        return listener.execEvent(t);
    }
}