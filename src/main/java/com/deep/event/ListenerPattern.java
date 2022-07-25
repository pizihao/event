package com.deep.event;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * 事件监听模式<br>
 * 他决定了使用哪一个执行器执行事件，该模式存在于上下文中
 *
 * @author Create by liuwenhao on 2022/7/20 12:40
 * @see BroadcastListenerPattern
 * @see ExclusiveListenerPattern
 */
@FunctionalInterface
public interface ListenerPattern {


	/**
	 * 决定了使用哪一个执行器执行事件<br>
	 * 将和事件进行绑定的所有监听器进行一次筛选。<br>
	 *
	 * @param type      事件类型
	 * @param listeners 该事件下所有的监听器集合
	 * @param <E>       事件类型
	 * @return 经监听模式筛选后的监听器集合
	 */
	@Nullable
	<E> Collection<Listener<E, Object>> getListener(Type type, Collection<Listener<E, Object>> listeners);

}
