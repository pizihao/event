package com.deep.listener;

import com.deep.event.RemoveEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2></h2>
 *
 * @author Create by liuwenhao on 2022/7/3 14:41
 */
public class CollListener implements Listener {

    @Override
    public Object execEvent(Object t) {
        System.out.println(t);
        RemoveEvent removeEvent1 = new RemoveEvent(this, "集合顺延方式1");
        RemoveEvent removeEvent2 = new RemoveEvent(this, "集合顺延方式2");
        List<RemoveEvent> list = new ArrayList<>();
        list.add(removeEvent1);
        list.add(removeEvent2);
        return list;
    }
}