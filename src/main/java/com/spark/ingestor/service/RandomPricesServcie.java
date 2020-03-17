package com.spark.ingestor.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spark.ingestor.model.RandomPrices;
import com.spark.ingestor.repository.RandomPricesRepository;
import com.spark.ingestor.utils.TimeFormatter;

import scala.Array;

@Service
public class RandomPricesServcie {

	@Autowired
	private RandomPricesRepository randomPricesRepository;
	
	public List<RandomPrices> getAllPrices() {
		return randomPricesRepository.findAll();
		
	}
	
	public List<RandomPrices> getPricesByDate(String date) {
		return randomPricesRepository.getPricesByDate(date);
	}

	public RandomPrices savePrice(RandomPrices price) {
		//price.setDate(LocalDate.now());
		price.setDate(TimeFormatter.getTimestamp());
		price.setTime(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		randomPricesRepository.save(price);
		return price;
		
	}
	
	public List<RandomPrices> saveAllPrices(List<Map> prices) {
		RandomPrices randomPrice = new RandomPrices();
		List<RandomPrices> randomPricesToSave = new ArrayList<RandomPrices>();
		prices.forEach(price -> {
			randomPrice.setHigh((double) price.get("high"));
			randomPrice.setHigh((double) price.get("low"));
			randomPrice.setHigh((double) price.get("open"));
			randomPrice.setHigh((double) price.get("last"));
			randomPrice.setDate(TimeFormatter.getTimestamp());
			randomPrice.setTime(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
			randomPricesToSave.add(randomPrice);
		});
		Iterable<RandomPrices> iterablePricesToSave = randomPricesToSave;
		randomPricesRepository.saveAll(iterablePricesToSave);
		return randomPricesToSave;
	}

}
