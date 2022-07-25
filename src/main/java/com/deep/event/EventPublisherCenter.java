package com.deep.event;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 默认的事件发布中心
 *
 * @author Create by liuwenhao on 2022/7/1 16:57
 */
public class EventPublisherCenter {

	/**
	 * 所有的上下文
	 */
	Map<String, EventContext> contextMap = new HashMap<>();

	/**
	 * 默认的上下文名称
	 */
	static final String DEFAULT_NAME = "DEFAULT_EVENT_CONTEXT";

	/**
	 * 构造
	 *
	 * @param contextMap 上下文映射表
	 */
	public EventPublisherCenter(Map<String, EventContext> contextMap) {
		this.contextMap = contextMap;
		this.contextMap.computeIfAbsent(DEFAULT_NAME, DefaultContext::new);
	}

	public EventPublisherCenter() {
		contextMap.put(DEFAULT_NAME, new DefaultContext(DEFAULT_NAME));
	}

	// ========================= 有关上下文的操作 =========================

	/**
	 * 在映射表中寻找一个上下文，如果不存在映射则返回null
	 *
	 * @param name 名称，上下文唯一标识
	 * @return name对应的上下文
	 */
	public EventContext getContext(String name) {
		Objects.requireNonNull(name);
		return contextMap.get(name);
	}

	/**
	 * 创建一个默认类型的上下文，如果上下文已存在则返回已存在的上下文实例
	 *
	 * @param name 名称，上下文唯一标识
	 * @return name对应的上下文
	 */
	public EventContext createContext(String name) {
		Objects.requireNonNull(name);
		return contextMap.computeIfAbsent(name, DefaultContext::new);
	}

	/**
	 * 将一个上下文添加到映射表中并返回。如果映射表中已经存在则根据参数确认是否覆盖
	 *
	 * @param context 需要添加映射的上下文
	 * @param cover   是否覆盖，true：覆盖
	 *                false：不覆盖
	 * @return 最终加入映射表的上下文
	 */
	public EventContext addContext(EventContext context, boolean cover) {
		Objects.requireNonNull(context);
		if (cover) {
			contextMap.put(context.name(), context);
			return context;
		} else {
			return contextMap.putIfAbsent(context.name(), context);
		}
	}

	/**
	 * 通过标识删除一个上下文并返回被删除的上下文，如果上下文原本就不在映射表中则返回null<br>
	 * 如果删除的是默认上下文，则会清空默认上下文并返回清空后的默认上下文
	 *
	 * @param name 上下文标识
	 * @return 被删除的上下文
	 */
	public EventContext removeContext(String name) {
		if (DEFAULT_NAME.equals(name)) {
			EventContext context = contextMap.get(DEFAULT_NAME);
			context.clear();
			return context;
		}
		return contextMap.remove(name);
	}

	// ========================= 事件和监听器操作 =========================

	/**
	 * 设置指定上下文中某个事件的监听模式，如果上下文不存在映射表中则无效
	 *
	 * @param name            上下文标识
	 * @param type            事件类型
	 * @param listenerPattern 监听模式
	 */
	public void setListenerPattern(String name, Type type, ListenerPattern listenerPattern) {
		if (contextMap.containsKey(name)) {
			EventContext context = contextMap.get(name);
			context.setListenerPattern(type, listenerPattern);
		}
	}

	/**
	 * @param listener 监听器
	 * @param <E>      事件类型
	 * @param <R>      监听器返回值
	 * @return ListenerModel
	 */
	public <E, R> ListenerModel<E, R> createListener(Listener<E, R> listener) {
		return ListenerModel.build(listener);
	}

	/**
	 * 在一个上下文将事件和监听器进行绑定，如果上下文不存在则创建
	 *
	 * @param name          上下文标识
	 * @param type          事件类型
	 * @param listenerModel 监听器包装信息，提供完整的监听器配置
	 * @param <E>           事件类型
	 * @param <R>           监听器返回值
	 */
	public <E, R> void bind(String name, Type type, ListenerModel<E, R> listenerModel) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(listenerModel);

		EventContext context = createContext(name);
		context.bind(type, listenerModel.listenerDecorate());
	}

	/**
	 * 在一个上下文将事件和监听器进行绑定，如果上下文不存在则创建
	 *
	 * @param name     上下文标识
	 * @param type     事件类型
	 * @param listener 监听器
	 * @param <E>      事件类型
	 * @param <R>      监听器返回值
	 */
	public <E, R> void bind(String name, Type type, Listener<E, R> listener) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(listener);

		EventContext context = createContext(name);
		ListenerDecorate<E, R> listenerDecorate = ListenerDecorate
			.<E, R>build()
			.listener(listener);
		context.bind(type, listenerDecorate);
	}

	/**
	 * 在默认上下文将事件和监听器进行绑定
	 *
	 * @param type          事件类型
	 * @param listenerModel 监听器包装信息，提供完整的监听器配置
	 * @param <E>           事件类型
	 * @param <R>           监听器返回值
	 */
	public <E, R> void bind(Type type, ListenerModel<E, R> listenerModel) {
		bind(DEFAULT_NAME, type, listenerModel);
	}

	/**
	 * 在默认上下文将事件和监听器进行绑定
	 *
	 * @param type     事件类型
	 * @param listener 监听器
	 * @param <E>      事件类型
	 * @param <R>      监听器返回值
	 */
	public <E, R> void bind(Type type, Listener<E, R> listener) {
		bind(DEFAULT_NAME, type, listener);
	}

	/**
	 * 在一个上下文中解绑事件和监听器
	 *
	 * @param name          上下文名称
	 * @param type          事件类型
	 * @param listenerModel 监听器包装信息，提供完整的监听器配置
	 * @param <E>           事件类型
	 * @param <R>           监听器返回值
	 */
	public <E, R> void unbind(String name, Type type, ListenerModel<E, R> listenerModel) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(listenerModel);
		EventContext context = createContext(name);
		context.unbind(type, listenerModel.listenerDecorate());
	}

	/**
	 * 在一个上下文中解绑事件和监听器
	 *
	 * @param name     上下文名称
	 * @param type     事件类型
	 * @param listener 监听器
	 * @param <E>      事件类型
	 * @param <R>      监听器返回值
	 */
	public <E, R> void unbind(String name, Type type, Listener<E, R> listener) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(listener);

		EventContext context = createContext(name);
		ListenerDecorate<E, R> listenerDecorate = ListenerDecorate
			.<E, R>build()
			.listener(listener);
		context.unbind(type, listenerDecorate);
	}

	/**
	 * 在默认上下文中解绑事件和监听器
	 *
	 * @param type          事件类型
	 * @param listenerModel 监听器包装信息，提供完整的监听器配置
	 * @param <E>           事件类型
	 * @param <R>           监听器返回值
	 */
	public <E, R> void unbind(Type type, ListenerModel<E, R> listenerModel) {
		unbind(DEFAULT_NAME, type, listenerModel);
	}

	/**
	 * 在默认上下文中解绑事件和监听器
	 *
	 * @param type     事件类型
	 * @param listener 监听器
	 * @param <E>      事件类型
	 * @param <R>      监听器返回值
	 */
	public <E, R> void unbind(Type type, Listener<E, R> listener) {
		unbind(DEFAULT_NAME, type, listener);
	}

	/**
	 * 解绑一个上下文中某个事件和所有监听器的关联
	 *
	 * @param name 上下文名称
	 * @param type 事件类型
	 */
	public void unbindAll(String name, Type type) {
		Objects.requireNonNull(name);
		EventContext context = createContext(name);
		context.unbindAll(type);
	}

	/**
	 * 解绑默认上下文中某个事件和所有监听器的关联
	 *
	 * @param type 事件类型
	 */
	public void unbindAll(Type type) {
		EventContext context = createContext(DEFAULT_NAME);
		context.unbindAll(type);
	}


	// ========================= 发布操作 =========================

	/**
	 * 在一个上下文中发布事件，如果上下文不存在则无效
	 *
	 * @param name  上下文名称
	 * @param event 事件实例
	 */
	public void publish(String name, Object event) {
		EventContext context = getContext(name);
		if (context != null) {
			context.publish(event);
		}
	}

	/**
	 * 在一个上下文中发布事件，如果上下文不存在则无效<br>
	 * 如果event是复杂的参数化类型对象，则可以使用type标注其具体类型
	 *
	 * @param name  上下文名称
	 * @param event 事件实例
	 * @param type  事件类型
	 */
	public void publish(String name, Object event, Type type) {
		EventContext context = getContext(name);
		if (context != null) {
			context.publish(event, type);
		}
	}

	/**
	 * 在默认上下文中发布事件，如果上下文不存在则无效
	 *
	 * @param event 事件实例
	 */
	public void publish(Object event) {
		publish(DEFAULT_NAME, event);
	}

	/**
	 * 在默认上下文中发布事件，如果上下文不存在则无效<br>
	 * 如果event是复杂的参数化类型对象，则可以使用type标注其具体类型
	 *
	 * @param event 事件实例
	 * @param type  事件类型
	 */
	public void publish(Object event, Type type) {
		publish(DEFAULT_NAME, event, type);
	}


	/**
	 * 生成一个Listener代理类
	 *
	 * @param o      代理的对象
	 * @param method 需要被代理的方法
	 * @return 监听器代理类
	 */
	private Listener newListenerProxy(Object o, Method method) {
		Class<?>[] classes = {Listener.class};
		ClassLoader classLoader = this.getClass().getClassLoader();
		return (Listener) Proxy.newProxyInstance(
			classLoader,
			classes,
			(p, m, a) -> {
				if ("execEvent".equals(m.getName())) {
					return method.invoke(o, a);
				}
				if (refuseGroup.contains(m.getName())) {
					return m.invoke(o, a);
				}
				return null;
			}
		);
	}


	/**
	 * 动态代理时忽略的方法
	 */
	static List<String> refuseGroup = new ArrayList<>();

	static {
		refuseGroup.add("hashCode");
		refuseGroup.add("equals");
		refuseGroup.add("clone");
		refuseGroup.add("toString");
		refuseGroup.add("finalize");
	}

}
