package com.deep.sparrow.event;

/**
 * <h2>添加事件</h2>
 *
 * @author Create by liuwenhao on 2022/6/29 17:40
 */
public class AddEvent extends Event {

    String name ;

    public AddEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AddEvent{" +
            "name='" + name + '\'' +
            '}';
    }
}