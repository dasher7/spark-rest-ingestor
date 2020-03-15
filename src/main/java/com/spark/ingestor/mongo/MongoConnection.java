package com.spark.ingestor.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.spark.ingestor.utils.Constants;

public class MongoConnection {

	private static MongoConnection mongoConnection = null;
	private MongoClient mongoClient = null;
	private MongoDatabase mongoDatabase = null;


	public MongoConnection() {
		mongoClient = new MongoClient(Constants.HOST);
		mongoDatabase = mongoClient.getDatabase(Constants.DATABASE);
	}

	public static MongoConnection getConnection() {
		return mongoConnection == null ? new MongoConnection() : mongoConnection;
	}

	public MongoDatabase getDatabase() {
		return mongoDatabase;
	}

}
