package com.spark.ingestor.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spark.ingestor.utils.TimeFormatter;

import lombok.Data;


@Data
@Document(collection = "randomprices")
public class RandomPrices {
	
	private double high;
	private double low;
	private double open;
	private double last;
	private long time;
	private String date;

}
