package com.deep.listener;

import com.deep.event.Event;

import java.util.EventListener;

/**
 * 监听器的顶级接口
 *
 * @author Create by liuwenhao on 2022/6/28 13:45
 */
@FunctionalInterface
public interface Listener<E extends Event, T extends Event> extends EventListener {

    /**
     * <h2>事件的执行</h2>
     *
     * @return Event，一个新的事件，如果返回的事件是本次事件、本次事件链中已经执行过的、null的话则停止事件传播
     * ，如果不是则事件会一直传播，知道返回上述三个事件体中的一个，注意:新的事件会传送给所有监听的监听器
     * ，可能会造成阻塞
     * @author liuwenhao
     * @date 2022/6/28 17:01
     */
    E execEvent(T t);

}
