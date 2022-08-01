package com.deep.event;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EdgeSpreadPatternTest {

	@Test
	public void testSpread() {
		TypeEventContext context = new TypeDefaultContext("name");
		BirdEvent birdEvent = new BirdEvent("鸟事件");
		BirdAsyncEvent birdAsyncEvent = new BirdAsyncEvent("异步鸟事件");
		List<BirdEvent> events = new ArrayList<>();
		events.add(birdEvent);
		// 集合按照元素的类型发布事件
		Listener<DogEvent, List<BirdEvent>> listener = ListenerDecorate.<DogEvent, List<BirdEvent>>build()
			.listener(e -> events);
		context.bind(DogEvent.class, listener);

		// 异步方法按照结果类型发布事件
		Listener<DogEvent, CompletableFuture<BirdAsyncEvent>> listener1 = ListenerDecorate
			.<DogEvent, CompletableFuture<BirdAsyncEvent>>build()
			.listener(e -> CompletableFuture.supplyAsync(() -> birdAsyncEvent));
		context.bind(DogEvent.class, listener1);

		Listener<BirdEvent, String> listener2 = ListenerDecorate.<BirdEvent, String>build()
			.listener(e -> {
				e.setName("鸟新事件");
				return e.getName();
			});
		Listener<BirdAsyncEvent, String> listener3 = ListenerDecorate.<BirdAsyncEvent, String>build()
			.listener(e -> {
				e.setName("异步鸟新事件");
				return e.getName();
			});
		context.bind(BirdEvent.class, listener2);
		context.bind(BirdAsyncEvent.class, listener3);
		DogEvent dogEvent = new DogEvent("狗事件");
		context.publish(dogEvent);
		Assert.assertEquals("鸟新事件", birdEvent.getName());
		Assert.assertEquals("异步鸟新事件", birdAsyncEvent.getName());
	}


	static class DogEvent {

		private String name;

		DogEvent(String name) {
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
			return "DogEvent{" +
				"name='" + name + '\'' +
				'}';
		}
	}

	static class BirdEvent {

		private String name;

		BirdEvent(String name) {
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
			return "BirdEvent{" +
				"name='" + name + '\'' +
				'}';
		}
	}

	static class BirdAsyncEvent {

		private String name;

		BirdAsyncEvent(String name) {
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
			return "BirdAsyncEvent{" +
				"name='" + name + '\'' +
				'}';
		}
	}
}
