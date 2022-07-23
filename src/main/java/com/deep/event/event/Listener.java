package com.deep.event.event;

import java.util.EventListener;

/**
 * 监听器 <br>
 * E 事件对象 <br>
 * R 返回对象 <br>
 * 如果返回的R和E不相等并且不为null，默认情况下会把R当做一个事件继续发布，直到没有监听这个的监听器为止<br>
 * 如果出现循环，则事件会一直传递下去，直到栈溢出
 *
 * @author Create by liuwenhao on 2022/7/19 20:43
 */
@FunctionalInterface
public interface Listener<E, R> extends EventListener {

	/**
	 * 执行事件
	 *
	 * @param e 事件对象
	 * @return Event，一个新的事件，如果返回的事件是本次事件或null的话则停止事件传播，如果不是则事件会一直传播，
	 * 直到返回上述两个事件体中的一个，注意:新的事件会按照传播模式传送给指定的监听器
	 */
	R execEvent(E e);

}
