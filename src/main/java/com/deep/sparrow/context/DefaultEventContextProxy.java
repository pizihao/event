package com.deep.sparrow.context;

import com.deep.sparrow.event.Event;
import com.deep.sparrow.listener.AsyncListenerDecorate;
import com.deep.sparrow.listener.Listener;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * 带有事件顺延的静态代理，如果某个监听器的返回值仍然是已经监控的事件类型，那么事件将会继续转发。
 * 如果出现循环，则这种机制会一直传播下去，直到栈溢出
 *
 * @author Create by liuwenhao on 2022/7/1 15:28
 */
public class DefaultEventContextProxy extends DefaultEventContext {

    List<Listener> listeners;

    Event event;

    public DefaultEventContextProxy(String name,
                                    List<Listener> listeners,
                                    Event event,
                                    EventBindMap eventBindMap) {
        super(name);
        this.eventBindMap.map = eventBindMap.map;
        this.listeners = listeners;
        this.event = event;
    }

    public DefaultEventContextProxy(String name,
                                    List<Listener> listeners,
                                    Event event,
                                    EventBindMap eventBindMap,
                                    Consumer<Throwable> throwableConsumer) {
        super(name, throwableConsumer);
        this.eventBindMap.map = eventBindMap.map;
        this.listeners = listeners;
        this.event = event;
    }


    /**
     * 实现事件的顺延，即重新发布一个事件
     *
     * @author liuwenhao
     * @date 2022/7/1 16:33
     */
    void doInvoke() {
        for (Listener listener : listeners) {
            if (listener instanceof AsyncListenerDecorate) {
                CompletableFuture.runAsync(() -> doExec(listener));
            } else {
                doExec(listener);
            }
        }
    }

    public void doExec(Listener listener) {
        try {
            publishEvents(listener.execEvent(event));
        } catch (ExecutionException | InterruptedException e) {
            exceptionally().accept(e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 对事件类型的控制。
     * 包括CompletionStage和Future的额外处理，因为其真正的对象是计算结果，而不是本身
     *
     * @param o 事件
     * @author liuwenhao
     * @date 2022/7/1 16:34
     */
    private void publishEvents(Object o) throws ExecutionException, InterruptedException {
        if (o == null) {
            return;
        }
        if (o instanceof CompletionStage) {
            ((CompletionStage<?>) o).thenAccept(e -> {
                if (e != null) {
                    publish(e);
                }
            });
        } else if (o instanceof Future) {
            publish(((Future<?>) o).get());
        } else {
            publishEvent(o);
        }

    }

    /**
     * 对集合或数组的额外处理，集合中的每一个元素都会触发一次事件执行
     *
     * @param o 事件
     * @author liuwenhao
     * @date 2022/7/1 16:36
     */
    private void publishEvent(Object o) {

        if (o.getClass().isArray()) {

            Object[] events = new Object[0];
            if (o instanceof Object[]) {
                events = (Object[]) o;
            }
            int length = Array.getLength(o);
            if (length > 0) {
                Class<?> wrapperType = Array.get(o, 0).getClass();
                Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
                for (int i = 0; i < length; i++) {
                    newArray[i] = Array.get(o, i);
                }
                events = newArray;
            }

            for (Object e : events) {
                publish(e);
            }
        } else if (o instanceof Collection<?>) {
            Collection<?> events = (Collection<?>) o;
            for (Object e : events) {
                publish(e);
            }
        } else {
            publish(o);
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}