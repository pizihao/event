package com.deep.listener;

import com.deep.event.AddEvent;
import com.deep.event.RemoveEvent;

/**
 * <h2></h2>
 *
 * @author Create by liuwenhao on 2022/7/3 16:54
 */
public class NonListener {

    @EventListener(AddEvent.class)
    public RemoveEvent toRemoveEvent(AddEvent addEvent) {
        System.out.println(addEvent);
        System.out.println("测试是否到达");
        return new RemoveEvent(this, "顺延到删除阶段");
    }

}