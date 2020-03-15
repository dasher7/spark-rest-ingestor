package com.spark.ingestor.parser;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class DocumentParser {

	public static String MapToStringWithGuava(Map mapToConvert) {
		return Joiner.on(",").withKeyValueSeparator("=").join(mapToConvert);
	}

	public static Map StringToMapWithStreams(String stringToConvert) {
		Map<String, String> map = Arrays.stream(stringToConvert.split(",")).map(entry -> entry.split("="))
				.collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
		return map;
	}

	public static Map StringToMapWithGuava(String stringToConvert) {
		return Splitter.on(',').withKeyValueSeparator('=')
				.split(stringToConvert.subSequence(1, stringToConvert.length() - 1));
	}

}
