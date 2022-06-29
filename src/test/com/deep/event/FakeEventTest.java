package com.deep.event;

import com.deep.listener.Listener;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class FakeEventTest extends TestCase {

    @Test
    public void testFakeEvent() {
        AddEvent addEvent = new AddEvent(this, "123");

        AddFakeEvent addFakeEvent1 = new AddFakeEvent("456");
        AddFakeEvent addFakeEvent2 = new AddFakeEvent("789");

        FakeEvent<AddFakeEvent> addFakeEventFakeEvent1 = new FakeEvent<>(this, addFakeEvent1);
        FakeEvent<AddFakeEvent> addFakeEventFakeEvent2 = new FakeEvent<>(this, addFakeEvent2);

        Assert.assertNotEquals(addEvent, addFakeEventFakeEvent1);
        Assert.assertEquals(addFakeEventFakeEvent1, addFakeEventFakeEvent2);

    }

}