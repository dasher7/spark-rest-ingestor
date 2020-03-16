package com.spark.ingestor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spark.ingestor.model.RandomPrices;
import com.spark.ingestor.service.RandomPricesServcie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

@Api(tags = "RandomPrices Controller")
@RestController
@RequestMapping("/prices")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RandomPricesController {

	@Autowired
	private RandomPricesServcie randomPricesServices;

	@GetMapping("/all")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "all prices found correctly", response = RandomPrices.class),
			@ApiResponse(code = 204, message = "had no error, but found no prices") })
	public ResponseEntity<Map> getAllPrices() {
		Map responseBody = new HashMap();
		List<RandomPrices> allPrices = randomPricesServices.getAllPrices();
		if (allPrices.size() <= 0) {
			responseBody.put("Count:", 0);
			responseBody.put("Data", "");
			responseBody.put("warning", "no data found");
			return new ResponseEntity<Map>(responseBody, HttpStatus.OK);
		}
		responseBody.put("Count:", allPrices.size());
		responseBody.put("Data", allPrices);
		responseBody.put("warning", "");
		responseBody.put("error", "");
		return new ResponseEntity<Map>(responseBody, HttpStatus.OK);

	}

}
