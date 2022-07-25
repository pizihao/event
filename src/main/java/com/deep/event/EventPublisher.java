package com.deep.event;

import java.lang.reflect.Type;

/**
 * <h2>事件发布</h2>
 *
 * @author Create by liuwenhao on 2022/7/20 13:21
 */
@FunctionalInterface
public interface EventPublisher {

	/**
	 * 发布事件<br>
	 * 接收事件时需要验证参数是否是自身事件所需的类型<br>
	 * 当事件类型为非参数化类型时
	 *
	 * @param o 事件实例
	 */
	default void publish(Object o) {
		publish(o, o.getClass());
	}

	/**
	 * 发布事件<br>
	 * 接收事件时需要验证参数是否是自身事件所需的类型<br>
	 * 本类可以适用于不同事件类型的监听
	 *
	 * @param o    事件实例
	 * @param type 事件类型
	 */
	void publish(Object o, Type type);

}
