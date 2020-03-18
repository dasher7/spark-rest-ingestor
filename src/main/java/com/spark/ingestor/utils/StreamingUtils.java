package com.spark.ingestor.utils;

public class StreamingUtils {
	
	public static boolean SWITCH = true;
	public static String HOST = "localhost";
	public static int PORT = 9000;
	public static final String HADOOP_CONNECTION = "hdfs://0.0.0.0:19000/test";
	
	public static int getPort() {
		return PORT;
	}
	public static void setPort(int port) {
		PORT = port;
	}

}
