package com.imageservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories(basePackages="com.imageservice.repository")
public class MongoConfig extends AbstractMongoConfiguration{
	
//	@Value("${spring.data.mongodb.host}")
	@Value("${spring.data.mongodb.uri}")
	private String mongoHost;
	
//	@Value("${spring.data.mongodb.port}")
//	private int mongoPort;
	
	@Value("${spring.data.mongodb.database}")
	private String database;

	@Override
	public MongoClient mongoClient() {
	    return new MongoClient(new MongoClientURI(mongoHost));
//		return new MongoClient(mongoHost, mongoPort);
	}

	@Override
	protected String getDatabaseName() {
		return database;
	}
	
	@Bean
	public GridFsTemplate getGridFSTemplate() throws Exception {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}

}
