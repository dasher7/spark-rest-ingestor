package com.spark.ingestor.service;

import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.bson.Document;
import org.springframework.stereotype.Service;

import com.spark.ingestor.mongo.MongoConnection;
import com.spark.ingestor.parser.DocumentParser;
import com.spark.ingestor.spark.SparkEngine;
import com.spark.ingestor.utils.StreamingUtils;


@Service
public class SparkService {
	
	private JavaStreamingContext streamingContext = null; // used for streaming context
	private JavaSparkContext sparkContext = null; // used for basic spark operation, stream not supported ( ? )
													// NOTE: only one context is availble as default implementation

	public boolean activateProducer() {
		
		StreamingUtils.SWITCH = StreamingUtils.SWITCH == true ? StreamingUtils.SWITCH : true;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					SparkEngine.initProducer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		return true;
	}

	public boolean disableProducer() {
		
		boolean disabled = StreamingUtils.SWITCH == true ? StreamingUtils.SWITCH == false : StreamingUtils.SWITCH == false;
		return !disabled;
		
	}

	public void activateStream() {
		SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("TickerCollector");
		streamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(1));
		//Logger.getRootLogger().setLevel(Level.ERROR);

		JavaReceiverInputDStream<String> inputStreamFromSource = streamingContext.socketTextStream(StreamingUtils.HOST,
				StreamingUtils.PORT);
		// inputStreamFromSource.print();

		/*
		 * TEST: 24/01/2020 Reading all data JSON-like data and saving it into mongo
		 */
		inputStreamFromSource.foreachRDD((rawEvents, time) -> {

			MongoConnection mongoConnection = MongoConnection.getConnection();
			String collection = "";
			List<String> eventList = rawEvents.collect();

			//if (eventList.size() > 0) {
				//Map randomPricesAsMap = DocumentParser.StringToMapWithGuava(eventList.get(0));
				//RandomPrices marketdata = new RandomPrices(marketdataAsMap);
				//RandomPrices randomPrice = new RandomPrices();
				//collection = ConfigController.findCollectionFromMarketdata(marketdata);
				//System.out.println("FOUND COLLECTION: " + collection);

			//}

			for (String event : eventList) {
				System.out.println("SAVING FOLLOWING EVENT INTO MONGO: " + event);
				Map map = DocumentParser.StringToMapWithGuava(event);
				Document document = new Document(map);
				mongoConnection.getDatabase().getCollection("randomprices").insertOne(document);
			}
		});

		new Thread(new Runnable() {
			@Override
			public void run() {
				streamingContext.start();
			}
		}).start();
		
	}

	public void disableStreaming() {
		streamingContext.stop();
		
	}
	
}
