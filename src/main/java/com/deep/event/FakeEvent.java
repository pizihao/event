package com.deep.event;

import java.util.Objects;

/**
 * <h2>伪事件</h2>
 * Event的子类，Event中的属性为事件发生的对象，此外FakeEvent还需要保存事件发送时产生的对象,
 * 因为此时的对象并不是完全的事件对象，所以称为伪事件
 *
 * @author Create by liuwenhao on 2022/6/28 17:46
 */
public class FakeEvent<E> extends Event {

    transient E event;

    public FakeEvent(Object source, E event) {
        super(source);
        this.event = event;
    }

    public E getEvent() {
        return event;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FakeEvent<?> fakeEvent = (FakeEvent<?>) o;
        return event.getClass().isAssignableFrom(fakeEvent.event.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, event.getClass());
    }

    @Override
    public String toString() {
        return event.toString();
    }

}