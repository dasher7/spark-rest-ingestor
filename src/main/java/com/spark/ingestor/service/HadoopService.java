package com.spark.ingestor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.ingestor.model.RandomPrices;
import com.spark.ingestor.repository.RandomPricesRepository;
import com.spark.ingestor.utils.StreamingUtils;
import com.spark.ingestor.utils.TimeFormatter;

@Service
public class HadoopService {
	
	/*
	 * used for basic spark operation, stream not supported ( ? )
	 * NOTE: only one context is availble as default implementation
	 * 
	 * private JavaSparkContext sparkContext = null;
	 * 
	 */
	
	private JavaStreamingContext streamingContext = null; // used for streaming context
	
	@Autowired
	private RandomPricesRepository randomPricesRepository;

	public void activateFileStream() {
		
		SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("TickerCollector");
		streamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(1));
		//Logger.getRootLogger().setLevel(Level.ERROR);

		JavaDStream<String> sparkFileStream = streamingContext.textFileStream(StreamingUtils.HADOOP_CONNECTION);
		// JavaDStream<String> sparkFileStream = streamingContext.textFileStream("C:\\Users\\user\\Desktop\\spark");

		Map randomPricesBody = new HashMap();
		
		sparkFileStream.foreachRDD(rdd -> {
			List<String> lines = rdd.collect();
			if (lines.size() > 0) {
				String[] keys = lines.get(0).split(",", 1);

				for (int i = 1; i < lines.size(); i++) {
					String[] line = lines.get(i).split(",", 1);
					for (int j = 0; j < line.length; j++) {
						randomPricesBody.put(keys[j], line[j]);
					}
					RandomPrices marketdata = new RandomPrices(randomPricesBody);
					randomPricesRepository.save(marketdata);
					randomPricesBody.clear();
				}
				System.out.println("SAVED " + lines.size() + " TO MONGODB, I AM A GOOD BOY!");
			} else {
				System.out.println("WAITING FOR FILE TO EAT .. I AM STARVING, C'MON MATE!! GIVE ME WAMRTH!");
			}
//			rdd.collect().forEach(elem -> {
//				String[] elemSplitted = elem.split(",", 1);
//				if (elemSplitted.length == 7) {
//					marktedataBody.put("ticker", elemSplitted[0]);
//					marktedataBody.put("bid", elemSplitted[5]);
//					marktedataBody.put("high", elemSplitted[6]);
//					marktedataBody.put("low", elemSplitted[7]);
//					MarketData marketdata = new MarketData(marktedataBody);
//					marketdataRepository.save(marketdata);
//					marktedataBody.clear();
//				}
//				System.out.println("ELEM: " + elem);
//			});
		});

		new Thread(new Runnable() {
			@Override
			public void run() {
				streamingContext.start();
			}
		}).start();

		System.out.println(TimeFormatter.getExtendedTimestamp());
		
	}

}
