package com.deep.context;

import com.deep.event.Event;
import com.deep.listener.Listener;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <h2>带有事件链的静态代理</h2>
 *
 * @author Create by liuwenhao on 2022/7/1 15:28
 */
public class DefaultEventContextProxy extends DefaultEventContext {

    Set<Listener> listeners;

    Event event;

    public DefaultEventContextProxy(String name, Set<Listener> listeners, Event event) {
        super(name);
        this.listeners = listeners;
        this.event = event;
    }

    void doInvoke() throws ExecutionException, InterruptedException {
        for (Listener listener : listeners) {
            publishEvents(listener.execEvent(event));
        }

    }

    private void publishEvents(Object o) throws ExecutionException, InterruptedException {

        if (o instanceof CompletionStage) {
            ((CompletionStage<?>) o).thenAccept(e -> {
                if (e != null) {
                    publish(event);
                }
            });
        } else if (o instanceof Future) {
            publish(((Future<?>) o).get());
        } else {
            publishEvent(o);
        }

    }

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
}