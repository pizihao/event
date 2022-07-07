package com.deep.sparrow.listener;


/**
 * <h2>异步执行装饰器</h2>
 * 事件直接使用{@link java.util.concurrent.CompletableFuture#runAsync(Runnable)}执行
 *
 *
 * @author Create by liuwenhao on 2022/7/6 17:06
 */
public class AsyncListenerDecorate implements Listener {

    /**
     * 监听器，{@link  OrderListenerDecorate}
     */
    OrderListenerDecorate listener;

    public AsyncListenerDecorate(OrderListenerDecorate listener) {
        this.listener = listener;
    }

    public OrderListenerDecorate getListener() {
        return listener;
    }

    @Override
    public Object execEvent(Object t) {
        return listener.execEvent(t);
    }

}