package com.deep.event;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * 类型事件上下文<br>
 * 事件和监听器的绑定，解绑，筛选监听器，处理事件等操作。<br>
 * 这是对{@link TypeEventPublisher}的一种实现，不直接使用{@link Event}而是使用事件的类型作为事件的标识<br>
 *
 * @author Create by liuwenhao on 2022/7/20 13:11
 */
public interface TypeEventContext extends EventContext<Type>, TypeEventPublisher {

}
