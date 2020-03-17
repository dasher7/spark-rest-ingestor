package com.spark.ingestor.spark;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import com.spark.ingestor.model.RandomPrices;
import com.spark.ingestor.utils.StreamingUtils;

public class StreamingServer implements Runnable {

	private BlockingQueue<RandomPrices> eventQueue;

	public StreamingServer(BlockingQueue<RandomPrices> eventQueue) {
		this.eventQueue = eventQueue;
	}

	public void run() {
		try {
			int port = StreamingUtils.getPort();
			ServerSocket serverSocket = new ServerSocket(port + 1);
			StreamingUtils.setPort(port + 1);
			Socket clientSocket = serverSocket.accept();
			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
			System.out.println("Starting server on " + StreamingUtils.PORT);
			while (true) {
				RandomPrices event = eventQueue.take();
				System.out.println(String.format("Writing \"%s\" to the socket at time %s.", event, new Date().getTime()));
				writer.println(event);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

