package com.spark.ingestor.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.spark.ingestor.model.RandomPrices;

public interface RandomPricesRepository extends MongoRepository<RandomPrices, String> {
	
	@Query("{ date: ?0 }")
	List<RandomPrices> getPricesByDate(String date);

}
