package com.spark.ingestor.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spark.ingestor.service.HadoopService;
import com.spark.ingestor.utils.TimeFormatter;

import io.swagger.annotations.Api;

@Api(tags = "Hadoop Controller")
@RestController
@RequestMapping("/hadoop")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HadoopController {

	@Autowired
	private HadoopService hadoopService;

	@GetMapping("/activateFileStream")
	public ResponseEntity<Map> activateHadoopFileStream() {

		Map responseBody = new HashMap();
		
		hadoopService.activateFileStream();

		responseBody.put("executed", "true");
		responseBody.put("time", new Date());
		return new ResponseEntity<Map>(responseBody, HttpStatus.OK);
	}

}
