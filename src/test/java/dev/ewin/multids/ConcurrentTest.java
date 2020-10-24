package dev.ewin.multids;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ConcurrentTest {

	@Test
	public void testCounter() {
		MyCounter counter = new MyCounter();
		for (int i = 0; i < 500; i++) {
			counter.increment();
		}
		assertEquals(500, counter.getCount());
	}
	
	@Test
	public void testCounterWithConcurrency() throws InterruptedException {
		int numberOfThreads = 1000;
		ExecutorService service = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		MyCounter counter = new MyCounter();
		for (int i = 0; i < numberOfThreads; i++) {
			service.execute(() -> {
				counter.increment();
				latch.countDown();
			});
		}
		latch.await();
		assertEquals(numberOfThreads, counter.getCount());
	}
	
	
	@Test
	public void testCounterWithConcurrencySync() throws InterruptedException {
		int numberOfThreads = 10000;
		ExecutorService service = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		MyCounter counter = new MyCounter();
		for (int i = 0; i < numberOfThreads; i++) {
			service.execute(() -> {
				counter.incrementSync();
				latch.countDown();
			});
		}
		latch.await();
		assertEquals(numberOfThreads, counter.getCount());
	}
	
	@Test
	public void testCounterWithAwaitTermination() throws InterruptedException {
		int numberOfThreads = 1000;
		ExecutorService service = Executors.newFixedThreadPool(10);
		MyCounter counter = new MyCounter();
		for (int i = 0; i < numberOfThreads; i++) {
			service.execute(() -> {
				counter.increment();
			});
		}
		service.shutdown();
		service.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		assertEquals(numberOfThreads, counter.getCount());
	}
	
	@Test
	public void testCounterWithConcurrencyWait() throws InterruptedException {
		int numberOfThreads = 2;
		ExecutorService service = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		MyCounter counter = new MyCounter();
		for (int i = 0; i < numberOfThreads; i++) {
			service.execute(() -> {
				try {
					counter.incrementWait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				latch.countDown();
			});
		}
		latch.await();
		assertEquals(numberOfThreads, counter.getCount());
	}

}
