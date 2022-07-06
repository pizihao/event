package com.deep.sparrow.context;

import com.deep.sparrow.event.AddEvent;
import com.deep.sparrow.event.RemoveEvent;
import com.deep.sparrow.listener.CollListener;
import com.deep.sparrow.listener.RemoveListener;
import org.junit.Test;

public class DefaultEventContextProxyTest {

    @Test
    public void testDoInvoke() {
        EventContext eventContext = new DefaultEventContext("测试");
        CollListener collListener = new CollListener();
        RemoveListener removeListener = new RemoveListener();
        eventContext.addListener(AddEvent.class, collListener);
        eventContext.addListener(RemoveEvent.class, removeListener);

        eventContext.publish(new AddEvent(this, "集合顺延测试"));
    }

}