package com.deep.listener;

import java.util.Objects;

/**
 * 针对Listener的装饰器
 * 添加 order 字段，标注其执行优先级，其数值越大执行优先级越低
 *
 * @author Create by liuwenhao on 2022/7/4 18:03
 */
public class ListenerDecorate implements Listener, Comparable<ListenerDecorate> {

    int order = 0;

    Listener listener;

    @Override
    public Object execEvent(Object t) {
        return listener.execEvent(t);
    }

    public ListenerDecorate(int order, Listener listener) {
        this.order = order;
        this.listener = listener;
    }

    @Override
    public int compareTo(ListenerDecorate o) {
        if (o == null) {
            return 1;
        }
        return o.order - this.order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ListenerDecorate that = (ListenerDecorate) o;
        return order == that.order && Objects.equals(listener, that.listener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, listener);
    }
}