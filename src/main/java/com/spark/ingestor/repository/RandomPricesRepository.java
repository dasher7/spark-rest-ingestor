package com.spark.ingestor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spark.ingestor.model.RandomPrices;

public interface RandomPricesRepository extends MongoRepository<RandomPrices, String> {

}
