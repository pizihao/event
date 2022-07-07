package com.deep.sparrow.listener;

import com.deep.sparrow.context.DefaultEventContext;
import com.deep.sparrow.event.AddEvent;

/**
 * @author Create by liuwenhao on 2022/7/7 22:17
 */
public class ThrowableListener implements Listener {

    @Override
    public Object execEvent(Object t) {
        AddEvent addEvent = (AddEvent) t;
        System.out.println(Integer.parseInt(addEvent.getName()));
        return null;
    }
}