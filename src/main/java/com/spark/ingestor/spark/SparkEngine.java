package com.spark.ingestor.spark;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.spark.ingestor.model.RandomPrices;
import com.spark.ingestor.utils.StreamingUtils;
import com.spark.ingestor.utils.TimeFormatter;

public class SparkEngine {
	
	private static final Executor SERVER_EXECUTOR = Executors.newSingleThreadExecutor();
	private static final String DELIMTER = ":";
	private static final long EVENT_PERIOD_SECONDS = 1;
	private static final Random random = new Random();

	// wrapper of the main method of EventServer, useful to be use to be called into rest API /activateProducer
	public static void initProducer() throws InterruptedException {

		// From doc: A bounded blocking queue backed by an array. This queue orders
		// elements FIFO (first-in-first-out).
		// BlockingQueue<String> eventQueue = new ArrayBlockingQueue<String>(100);
		BlockingQueue<RandomPrices> eventQueue = new ArrayBlockingQueue<RandomPrices>(100);
		SERVER_EXECUTOR.execute(new StreamingServer(eventQueue));

		while (StreamingUtils.SWITCH) {
			// eventQueue.put(generateEvent());
			eventQueue.put(generateRandomPrices());
			// Thread.sleep(TimeUnit.SECONDS.toMillis(EVENT_PERIOD_SECONDS));
		}
	}

	/*
	 * ALL THE FOLLOWING GENERATE ARE METHOD USED TO GENERATE DATA
	 */

	private static RandomPrices generateRandomPrices() {
		RandomPrices randomPrice = new RandomPrices();
		randomPrice.setDate(TimeFormatter.getTimestamp());
		randomPrice.setTime(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		randomPrice.setHigh(random.nextDouble());
		randomPrice.setLow(random.nextDouble());
		randomPrice.setLast(random.nextDouble());
		randomPrice.setOpen(random.nextDouble());
		return randomPrice;
	}

}
