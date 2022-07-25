package com.deep.event;


import java.util.function.Function;

/**
 * 创建监听器的辅助类
 *
 * @author Create by liuwenhao on 2022/7/23 18:27
 */
public class ListenerModel<E, R> {

	/**
	 * 优先级，数值越大优先级越低
	 */
	int order;

	/**
	 * 是否异步执行
	 */
	Boolean isAsync;

	/**
	 * 传播模式
	 */
	SpreadPattern spreadPattern;

	/**
	 * 异常处理
	 */
	Function<Throwable, R> fn;

	/**
	 * 监听器
	 */
	Listener<E, R> listener;

	private ListenerModel(Listener<E, R> listener) {
		this.listener = listener;
	}

	protected static <E, R> ListenerModel<E, R> build(Listener<E, R> listener) {
		return new ListenerModel<>(listener);
	}

	public ListenerModel<E, R> order(int order) {
		this.order = order;
		return this;
	}

	public ListenerModel<E, R> async(Boolean async) {
		isAsync = async;
		return this;
	}

	public ListenerModel<E, R> spreadPattern(SpreadPattern spreadPattern) {
		this.spreadPattern = spreadPattern;
		return this;
	}

	public ListenerModel<E, R> fn(Function<Throwable, R> fn) {
		this.fn = fn;
		return this;
	}

	public ListenerModel<E, R> listener(Listener<E, R> listener) {
		this.listener = listener;
		return this;
	}

	public int getOrder() {
		return order;
	}

	public Boolean isAsync() {
		return isAsync;
	}

	public SpreadPattern getSpreadPattern() {
		return spreadPattern;
	}

	public Function<Throwable, R> getFn() {
		return fn;
	}

	public Listener<E, R> getListener() {
		return listener;
	}

	/**
	 * 装换，将ListenerModel转换为ListenerDecorate，并设置默认值
	 *
	 * @return ListenerDecorate
	 */
	protected ListenerDecorate<E, R> listenerDecorate() {
		ListenerDecorate<E, R> listenerDecorate = ListenerDecorate.build();

		int decorateOrder = getOrder() == 0 ? listenerDecorate.order : getOrder();
		boolean decorateAsync = isAsync() == null ? listenerDecorate.async : isAsync();
		Function<Throwable, R> decorateFn = getFn() == null ? listenerDecorate.getThrowHandler() : getFn();
		SpreadPattern decorateSpreadPattern = getSpreadPattern() == null ? listenerDecorate.getSpreadPattern() : getSpreadPattern();

		return listenerDecorate
			.listener(getListener())
			.fn(decorateFn)
			.async(decorateAsync)
			.order(decorateOrder)
			.spreadPattern(decorateSpreadPattern);
	}
}
