package com.spark.ingestor.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "randomprices")
public class RandomPrices {
	
	private double high;
	private double low;
	private double open;
	private double last;
	private long time;
	private Date date;

}
