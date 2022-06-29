package com.deep.chain;

import com.deep.event.Event;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <h2>事件链</h2>
 * 一次事件执行的事件缓存，一次事件链中的事件必须是不重复的事件组成的链。
 * 链中的数据是在事件执行完成后加入的，和事件的执行顺序无关。
 *
 * @author Create by liuwenhao on 2022/6/28 17:10
 */
public class EventChain<T extends Event> {

    /**
     * 已经执行过的事件，内部包含了事件和伪事件。
     * 因为事件的具体类型是事件还是伪事件只有在事件真正发布的时候才能确定
     * 所以必须实现{@link com.deep.event.FakeEvent#equals(Object)}
     */
    Set<T> events = new CopyOnWriteArraySet<>();

    /**
     * 添加一个事件到事件链中，表示该事件已经在本次执行中完成
     *
     * @param t 事件
     * @author liuwenhao
     * @date 2022/6/29 9:00
     */
    public void addEvents(T t) {
        events.add(t);
    }

    /**
     * 判断事件是否存在于当前事件链中
     *
     * @param t 事件
     * @return boolean
     * @author liuwenhao
     * @date 2022/6/29 9:14
     */
    public boolean contains(T t) {
        return events.contains(t);
    }

    public EventChain() {
    }
}