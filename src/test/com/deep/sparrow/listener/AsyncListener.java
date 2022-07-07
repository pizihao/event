package com.deep.sparrow.listener;

import com.deep.sparrow.event.AddEvent;

import java.util.concurrent.TimeUnit;

/**
 * <h2>异步执行</h2>
 *
 * @author Create by liuwenhao on 2022/7/7 14:55
 */
public class AsyncListener {

    @EventListener(value = AddEvent.class, order = 1, isAsync = true)
    public Object execAsyncEvent(AddEvent t) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        System.out.println("等待完成" + t);
        return null;
    }

    @EventListener(value = AddEvent.class, order = 2, isAsync = true)
    public Object execEvent(AddEvent t) {
        System.out.println("同步执行" + t);
        return null;
    }


}