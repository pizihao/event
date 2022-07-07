package java.com.deep.sparrow.listener;

import java.util.Objects;

/**
 * 针对Listener的装饰器
 * 添加 order 字段，标注其执行优先级，其数值越大执行优先级越低,
 * 在进行监听器对比时以内部监听器为准，不会考虑order
 *
 * @author Create by liuwenhao on 2022/7/4 18:03
 */
public class OrderListenerDecorate implements Listener, Comparable<OrderListenerDecorate> {

    int order = 0;

    Listener listener;

    @Override
    public Object execEvent(Object t) {
        return listener.execEvent(t);
    }

    public OrderListenerDecorate(int order, Listener listener) {
        this.order = order;
        this.listener = listener;
    }

    public OrderListenerDecorate(Listener listener) {
        this.listener = listener;
    }

    public Listener getListener() {
        return listener;
    }

    @Override
    public int compareTo(OrderListenerDecorate o) {
        if (o == null) {
            return 1;
        }
        return this.order - o.order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }
        if (getClass() == o.getClass()) {
            OrderListenerDecorate that = (OrderListenerDecorate) o;
            return Objects.equals(listener, that.listener);
        }

        if (Listener.class.isAssignableFrom(o.getClass())) {
            Listener objListener = (Listener) o;
            return Objects.equals(listener, objListener);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return listener.hashCode();
    }
}