package com.deep.publisher;


import com.deep.context.DefaultEventContext;
import com.deep.context.EventContext;
import com.deep.event.AddEvent;
import com.deep.listener.AddListener;
import com.deep.listener.Listener;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

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
}