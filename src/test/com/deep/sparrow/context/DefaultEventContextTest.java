package com.deep.sparrow.context;

import com.deep.sparrow.event.AddEvent;
import com.deep.sparrow.event.AddFakeEvent;
import com.deep.sparrow.event.RemoveEvent;
import com.deep.sparrow.listener.AddListener;
import com.deep.sparrow.listener.Listener;
import com.deep.sparrow.listener.RemoveListener;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DefaultEventContextTest {

    @Test
    public void testAddListener() {
        EventContext eventContext = new DefaultEventContext("测试");
        AddListener listenerTest = new AddListener();
        eventContext.addListener(AddEvent.class, listenerTest);
        List<Listener> listeners = eventContext.getListeners(AddEvent.class);
        Assert.assertTrue(listeners.contains(listenerTest));
    }


    @Test
    public void testGetListeners() {
        EventContext eventContext = new DefaultEventContext("测试");
        AddListener listenerTest1 = new AddListener();
        AddListener listenerTest2 = new AddListener();
        eventContext.addListener(AddEvent.class, listenerTest1);
        eventContext.addListener(AddEvent.class, listenerTest2);
        eventContext.addListener(RemoveEvent.class, listenerTest2);
        List<Listener> listeners = eventContext.getListeners(AddEvent.class);
        List<Listener> listeners1 = eventContext.getListeners();
        Assert.assertEquals(2, listeners.size());
        Assert.assertEquals(3, listeners1.size());
    }

    @Test
    public void testClean() {
        EventContext eventContext = new DefaultEventContext("测试");
        AddListener listenerTest1 = new AddListener();
        AddListener listenerTest2 = new AddListener();
        eventContext.addListener(AddEvent.class, listenerTest1);
        eventContext.addListener(AddEvent.class, listenerTest2);
        eventContext.clean();
        List<Listener> listeners = eventContext.getListeners();
        Assert.assertEquals(0, listeners.size());
    }

    @Test
    public void testRemove() {
        EventContext eventContext = new DefaultEventContext("测试");
        AddListener listenerTest1 = new AddListener();
        AddListener listenerTest2 = new AddListener();
        eventContext.addListener(AddEvent.class, listenerTest1);
        eventContext.addListener(AddEvent.class, listenerTest2);
        List<Listener> listeners = eventContext.getListeners();
        Assert.assertEquals(2, listeners.size());
        eventContext.remove(AddEvent.class, listenerTest1);
        eventContext.remove(RemoveEvent.class, listenerTest1);
        List<Listener> listeners1 = eventContext.getListeners();
        Assert.assertEquals(1, listeners1.size());
    }

    @Test
    public void testPublish() {
        EventContext eventContext = new DefaultEventContext("测试");
        AddListener addListenerTest = new AddListener();
        RemoveListener removeListenerTest = new RemoveListener();
        eventContext.addListener(AddEvent.class, addListenerTest);
        eventContext.addListener(RemoveEvent.class, removeListenerTest);
        eventContext.addListener(AddFakeEvent.class, addListenerTest);
        AddEvent addEvent = new AddEvent(this, "测试事件");
        AddFakeEvent addFakeEvent = new AddFakeEvent("不是事件类型的情况");
        eventContext.publish(addEvent);
        eventContext.publish(addFakeEvent);
    }
}