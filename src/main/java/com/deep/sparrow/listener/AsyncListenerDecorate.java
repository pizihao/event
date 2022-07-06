package com.deep.sparrow.listener;

import com.deep.sparrow.util.ExecutorUtil;

import javax.annotation.Resource;
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

    @Resource
    List<ThreadPoolExecutor> poolExecutors;

    Listener listener;

    public AsyncListenerDecorate(Listener listener) {
        this.listener = listener;
    }

    public AsyncListenerDecorate(List<ThreadPoolExecutor> poolExecutors, Listener listener) {
        this.poolExecutors = poolExecutors;
        this.listener = listener;
    }


    @Override
    public Object execEvent(Object t) {
        return listener.execEvent(t);
    }
}