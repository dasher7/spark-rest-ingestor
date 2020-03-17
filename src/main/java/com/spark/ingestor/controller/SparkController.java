package com.spark.ingestor.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spark.ingestor.service.SparkService;
import com.spark.ingestor.spark.SparkEngine;
import com.spark.ingestor.utils.StreamingUtils;

import io.swagger.annotations.Api;

@Api(tags = "SparkController")
@RestController
@RequestMapping("/spark")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SparkController {

	@Autowired
	private SparkService sparkService;

	@GetMapping("/activateProducer")
	public ResponseEntity<Map> activateProducer() {

		Map responseBody = new HashMap();

		boolean activated = sparkService.activateProducer();

		if (activated) {
			responseBody.put("active", "true");
			responseBody.put("timeOfActivation", new Date());
		}

		return new ResponseEntity<Map>(responseBody, HttpStatus.OK);

	}

	@GetMapping("/disableProducer")
	public ResponseEntity<Map> disableProducer() {
		Map responseBody = new HashMap();
		ResponseEntity<Map> response = null;
		boolean disabled = sparkService.disableProducer();
		
		if (disabled) {
			responseBody.put("swtichValue", StreamingUtils.SWITCH);
			responseBody.put("wasSwitched", true);
			responseBody.put("timeOfSwitch", new Date());
			response = new ResponseEntity<Map>(responseBody, HttpStatus.OK);
		} else {
			responseBody.put("error", "SWITCH value already false");
			responseBody.put("swtichValue", StreamingUtils.SWITCH);
			responseBody.put("timeOfSwitch", new Date());
			response = new ResponseEntity<Map>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}

	@GetMapping("/activateStream")
	public ResponseEntity<Map> activateMapping() {
		Map responseBody = new HashMap();
		ResponseEntity<Map> response = null;
		sparkService.activateStream();

		responseBody.put("executed", "true");
		responseBody.put("time", new Date());
		response = new ResponseEntity<Map>(responseBody, HttpStatus.OK);
		return response;
	}

	@GetMapping("/disableStreaming")
	public ResponseEntity<Map> disableMapping() {
		Map responseBody = new HashMap();
		sparkService.disableStreaming();
		responseBody.put("executed", "true");
		responseBody.put("time", new Date());
		return new ResponseEntity<Map>(responseBody, HttpStatus.OK);
	}

	@GetMapping("/getSwitchValue")
	public ResponseEntity<Map> getSwitchValue() {
		Map response = new HashMap();
		response.put("switchValue", StreamingUtils.SWITCH);
		return new ResponseEntity<Map>(response, HttpStatus.OK);
	}

}
