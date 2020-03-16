package com.spark.ingestor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.ingestor.model.RandomPrices;
import com.spark.ingestor.repository.RandomPricesRepository;

@Service
public class RandomPricesServcie {

	@Autowired
	private RandomPricesRepository randomPricesRepository;
	
	public List<RandomPrices> getAllPrices() {
		
		return randomPricesRepository.findAll();
		
	}

}
