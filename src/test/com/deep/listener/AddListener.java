package com.deep.listener;

import com.deep.event.RemoveEvent;

public class AddListener implements Listener {

    @Override
    public Object execEvent(Object t) {
        System.out.println(t);
        return new RemoveEvent(this, "顺延下");
    }
}