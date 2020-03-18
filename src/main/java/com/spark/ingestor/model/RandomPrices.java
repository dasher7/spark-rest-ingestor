package com.spark.ingestor.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "randomprices")
public class RandomPrices extends HashMap<String, Object>{
	
	private double high;
	private double low;
	private double open;
	private double last;
	private long time;
	private String date;
	
	public RandomPrices(Map map) {
		super(map);
	}

	public RandomPrices() {

	}

}
