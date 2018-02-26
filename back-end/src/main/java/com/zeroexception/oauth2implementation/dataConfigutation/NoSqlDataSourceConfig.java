package com.zeroexception.oauth2implementation.dataConfigutation;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


@Configuration
public class NoSqlDataSourceConfig {

    private static MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.uri}")
    private String mongodbUri;
    @Value("${spring.data.mongodb.database}")
    private String dbName;

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate(){
        if (mongoTemplate == null) {
            mongoTemplate = new MongoTemplate(new MongoClient(new MongoClientURI(this.mongodbUri)), this.dbName);
        }
        return NoSqlDataSourceConfig.mongoTemplate;
    }

}

