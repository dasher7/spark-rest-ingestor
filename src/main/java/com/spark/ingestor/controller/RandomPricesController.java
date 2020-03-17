package com.spark.ingestor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spark.ingestor.model.RandomPrices;
import com.spark.ingestor.service.RandomPricesServcie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "RandomPrices Controller")
@RestController
@RequestMapping("/prices")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RandomPricesController {

	@Autowired
	private RandomPricesServcie randomPricesServices;

	@GetMapping("/all")
	@ApiOperation("Get all the prices")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "all prices found correctly", response = RandomPrices.class),
			@ApiResponse(code = 204, message = "had no error, but found no prices") })
	public ResponseEntity<Map> getAllPrices() {
		Map responseBody = new HashMap();
		List<RandomPrices> allPrices = randomPricesServices.getAllPrices();
		if (allPrices.size() <= 0) {
			responseBody.put("count:", 0);
			responseBody.put("data", "");
			responseBody.put("warning", "no data found");
			return new ResponseEntity<Map>(responseBody, HttpStatus.OK);
		}
		responseBody.put("count:", allPrices.size());
		responseBody.put("data", allPrices);
		responseBody.put("warning", "");
		responseBody.put("error", "");
		return new ResponseEntity<Map>(responseBody, HttpStatus.OK);

	}
	
	@GetMapping("/findByDate/{date}")
	@ApiOperation("Get all the prices saved in the date passed in the param")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Found prices for that date"),
			@ApiResponse(code = 400, message = "Parameter is in an incorrect format")
	})
	public ResponseEntity<Map> getPricesByDate(@PathVariable("date") String date) {
		Map responseBody = new HashMap();
		List<RandomPrices> prices = randomPricesServices.getPricesByDate(date);
		responseBody.put("count", prices.size());
		responseBody.put("data", prices);
		responseBody.put("warning", "");
		responseBody.put("error", "");
		return new ResponseEntity<Map>(responseBody, HttpStatus.OK);
	}

	@PostMapping("/save")
	@ApiOperation("Save the price passed in the body - no need to pass time and date")
	@ApiImplicitParams({ @ApiImplicitParam(name = "prices", dataType = "RandomPrices") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Price saved", response = RandomPrices.class),
			@ApiResponse(code = 400, message = "Incorrect format") })
	public ResponseEntity<Map> savePrice(@RequestBody RandomPrices price) {
		Map responseBody = new HashMap();
		RandomPrices randomPrice = randomPricesServices.savePrice(price);
		responseBody.put("count", 1);
		responseBody.put("data", randomPrice);
		responseBody.put("warning", "");
		responseBody.put("error", "");
		return new ResponseEntity<Map>(responseBody, HttpStatus.OK);
	}

	@PostMapping("/saveAll")
	@ApiOperation("Save all the RandomPrices passed into the data keyword. Use it for save network throughput")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Prices saved"),
			@ApiResponse(code = 422, message = "Incorrect JSON format, pass all the prices into data keyword")
	})
	public ResponseEntity<Map> saveAllPrices(@RequestBody Map body) {
		Map responseBody = new HashMap();
		if (!body.containsKey("data")) {
			responseBody.put("count:", 0);
			responseBody.put("data", "[]");
			responseBody.put("warning", "JSON incorrect format");
			responseBody.put("error", "you should pass a key called data within an array o RandomPrices");
			return new ResponseEntity<Map>(responseBody, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		List<Map> data = (List<Map>) body.get("data");
		List<RandomPrices> savedPrices = randomPricesServices.saveAllPrices(data);
		if (savedPrices.size() <= 0) {
			responseBody.put("count:", 0);
			responseBody.put("data", "");
			responseBody.put("warning", "no data saved");
			responseBody.put("error", "");
			return new ResponseEntity<Map>(responseBody, HttpStatus.OK);
		}
		responseBody.put("count:", savedPrices.size());
		responseBody.put("data", savedPrices);
		responseBody.put("warning", "");
		responseBody.put("error", "");
		return new ResponseEntity<Map>(responseBody, HttpStatus.OK);
	}
}
