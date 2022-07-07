package com.deep.sparrow.listener;

import com.deep.sparrow.util.ExecutorUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <h2>异步执行装饰器</h2>
 *
 * @author Create by liuwenhao on 2022/7/6 17:06
 */
public class AsyncListenerDecorate implements Listener {

    /**
     * 可能会用到的线程池
     */
    @Resource
    List<ThreadPoolExecutor> poolExecutors;

    /**
     * 监听器，{@link  OrderListenerDecorate}
     */
    OrderListenerDecorate listener;

    public AsyncListenerDecorate(OrderListenerDecorate listener) {
        this(new ArrayList<>(), listener);
    }

    public AsyncListenerDecorate(ThreadPoolExecutor poolExecutor, OrderListenerDecorate listener) {
        this(new ArrayList<>(), listener);
        this.poolExecutors.add(poolExecutor);
    }

    public AsyncListenerDecorate(List<ThreadPoolExecutor> poolExecutors, OrderListenerDecorate listener) {
        this.poolExecutors = poolExecutors;
        this.listener = listener;
    }

    /**
     * 获取一个相对空闲的线程池
     * 如果无法获取线程池，则直接使用{@link Executors#newSingleThreadExecutor()}
     *
     * @return ThreadPoolExecutor
     */
    public ThreadPoolExecutor getExecutor() {
        ThreadPoolExecutor poolExecutor = ExecutorUtil.idleService(poolExecutors);

        return Objects.isNull(poolExecutor)
            ? (ThreadPoolExecutor) Executors.newFixedThreadPool(1)
            : poolExecutor;
    }

    public OrderListenerDecorate getListener() {
        return listener;
    }


    @Override
    public Object execEvent(Object t) {
        return listener.execEvent(t);
    }

}