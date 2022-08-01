package com.deep.event;

import java.lang.reflect.Type;
import java.util.*;

/**
 * 独占模式<br>
 * 仅将事件传递给优先级最高的监听器，如果存在多个同等优先级的监听器则传递给第一个<br>，
 *
 * @author Create by liuwenhao on 2022/7/21 14:23
 */
public class ExclusiveListenerPattern implements ListenerPattern {

    @Override
    public <E> Collection<Listener<E, Object>> getListener(Type type, Collection<Listener<E, Object>> listeners) {
        if (listeners == null || listeners.isEmpty()) {
            return Collections.emptyList();
        }
        List<Listener<E, Object>> listenerList = new ArrayList<>();
        for (Listener<E, Object> listener : listeners) {
            listenerList.add(listener);
            break;
        }
        return listenerList;
    }
}
