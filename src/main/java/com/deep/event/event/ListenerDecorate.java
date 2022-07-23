package com.deep.event.event;

import java.util.Objects;
import java.util.function.Function;

/**
 * 监听器的包装器，提供了对监听器的扩展：<br>
 * <ul>
 *  	<li>优先级</li>
 *  	<li>是否异步执行</li>
 *  	<li>监听模式</li>
 *  	<li>传播模式</li>
 *  	<li>异常处理</li>
 *  	<li>...</li>
 * </ul>
 *
 * @author Create by liuwenhao on 2022/7/20 9:07
 */
class ListenerDecorate<E, R> implements Listener<E, R>, Comparable<ListenerDecorate<E, R>> {

	/**
	 * 优先度，默认为0
	 */
	int order = 0;

	/**
	 * 是否异步执行，true：异步执行，false：同步执行<br>
	 * 默认为同步执行
	 */
	boolean async = false;

	/**
	 * 事件传播模式
	 */
	SpreadPattern spreadPattern;

	/**
	 * 监听器
	 */
	Listener<E, R> listener;

	/**
	 * 异常的处理
	 */
	Function<Throwable, R> fn = t -> {
		throw new RuntimeException(t);
	};

	@Override
	public R execEvent(E e) {
		return listener.execEvent(e);
	}

	public static <E, R> ListenerDecorate<E, R> build() {
		return new ListenerDecorate<>();
	}

	public ListenerDecorate<E, R> order(int order) {
		this.order = order;
		return this;
	}

	public ListenerDecorate<E, R> async(boolean async) {
		this.async = async;
		return this;
	}

	public ListenerDecorate<E, R> spreadPattern(SpreadPattern spreadPattern) {
		this.spreadPattern = spreadPattern;
		return this;
	}

	public ListenerDecorate<E, R> listener(Listener<E, R> listener) {
		this.listener = listener;
		return this;
	}

	public ListenerDecorate<E, R> fn(Function<Throwable, R> fn) {
		this.fn = fn;
		return this;
	}

	public boolean isAsync() {
		return async;
	}

	public SpreadPattern getSpreadPattern() {
		return spreadPattern;
	}

	public Function<Throwable, R> getThrowHandler() {
		return fn;
	}

	public Listener<E, R> getListener() {
		return listener;
	}

	@Override
	public int compareTo(ListenerDecorate o) {
		if (o == null) {
			return 1;
		}
		return this.order - o.order;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ListenerDecorate<E, R> that = (ListenerDecorate<E, R>) o;
		return order == that.order && Objects.equals(listener, that.listener);
	}


	@Override
	public int hashCode() {
		return listener.hashCode();
	}
}
