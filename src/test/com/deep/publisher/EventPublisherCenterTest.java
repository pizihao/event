package com.deep.sparrow.publisher;


import com.deep.context.DefaultEventContext;
import com.deep.context.EventContext;
import com.deep.event.AddEvent;
import com.deep.event.RemoveEvent;
import com.deep.listener.*;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EventPublisherCenterTest {

    @Test
    public void testAddListener() {
        EventPublisherCenter center = new EventPublisherCenter();
        EventContext eventContext1 = new DefaultEventContext("1");
        AddListener addListener = new AddListener();
        center.addListener("1", addListener, AddEvent.class);
        EventContext context = center.getContext("1");
        Assert.assertEquals(eventContext1, context);
    }

    @Test
    public void testTestAddListener() {
        EventPublisherCenter center = new EventPublisherCenter();
        EventContext eventContext1 = new DefaultEventContext("1");
        EventContext eventContext2 = new DefaultEventContext("2");
        center.addContext(eventContext1);
        center.addContext(eventContext2);
        AddListener addListener = new AddListener();
        center.addListener(addListener, AddEvent.class);
        EventContext context1 = center.getContext("1");
        EventContext context2 = center.getContext("2");
        List<Listener> listener1 = context1.getListeners();
        List<Listener> listener2 = context2.getListeners();
        Assert.assertEquals(2, listener1.size() + listener2.size());
    }

    @Test
    public void testTestRemoveListener() {
        EventPublisherCenter center = new EventPublisherCenter();
        EventContext eventContext1 = new DefaultEventContext("1");
        EventContext eventContext2 = new DefaultEventContext("2");
        center.addContext(eventContext1);
        center.addContext(eventContext2);
        AddListener addListener = new AddListener();
        center.addListener(addListener, AddEvent.class);
        center.removeListener("1", addListener, AddEvent.class);
        EventContext context1 = center.getContext("1");
        EventContext context2 = center.getContext("2");
        List<Listener> listener1 = context1.getListeners();
        List<Listener> listener2 = context2.getListeners();
        Assert.assertEquals(1, listener1.size() + listener2.size());

    }

    @Test
    public void testRemoveContext() {
        EventPublisherCenter center = new EventPublisherCenter();
        EventContext eventContext1 = new DefaultEventContext("1");
        EventContext eventContext2 = new DefaultEventContext("2");
        center.addContext(eventContext1);
        center.addContext(eventContext2);
        AddListener addListener = new AddListener();
        center.addListener(addListener, AddEvent.class);
        center.removeContext("1");
        EventContext context1 = center.getContext("1");
        EventContext context2 = center.getContext("2");
        List<Listener> listener2 = context2.getListeners();
        Assert.assertNull(context1);
        Assert.assertEquals(1, listener2.size());
    }

    @Test
    public void testTestAddListener1() {
        EventPublisherCenter center = new EventPublisherCenter();
        NonListener listener = new NonListener();
        List<Method> collect = Arrays.stream(listener.getClass().getDeclaredMethods())
            .filter(m -> m.getAnnotation(EventListener.class) != null)
            .collect(Collectors.toList());

        collect.forEach(method -> {
            EventListener eventListener = method.getAnnotation(EventListener.class);
            center.addListener("测试", eventListener, listener, method);
        });
        AddEvent addEvent = new AddEvent(this, "注解方式");
        RemoveListener removeListener = new RemoveListener();
        center.addListener("测试", removeListener, RemoveEvent.class);

        center.publish("测试", addEvent);

    }

    @Test
    public void testTestAddListener2() {
    }
}