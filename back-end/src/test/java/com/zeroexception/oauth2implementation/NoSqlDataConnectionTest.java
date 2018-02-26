package com.zeroexception.oauth2implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class NoSqlDataConnectionTest {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void contextLoads() {

    }

    @Test
    public void writeToMongoDB() {
        mongoTemplate.dropCollection("test");
        mongoTemplate.createCollection("test");
        mongoTemplate.insert(new SampleObject(Instant.now().getEpochSecond(), "Sample"),
                "test");
    }
}
