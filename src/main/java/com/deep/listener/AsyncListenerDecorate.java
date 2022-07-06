package com.deep.listener;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * <h2>异步执行装饰器</h2>
 *
 * @author Create by liuwenhao on 2022/7/6 17:06
 */
public class AsyncListenerDecorate implements Listener {

    @Resource
    ExecutorService executorService;

    @Override
    public Object execEvent(Object t) {
        return null;
    }
}