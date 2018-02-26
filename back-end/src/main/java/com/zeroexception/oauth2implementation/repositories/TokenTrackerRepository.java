package com.zeroexception.oauth2implementation.repositories;

import com.zeroexception.oauth2implementation.model.TokenTracker;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Viet Quoc Tran
 * www.zeroexception.com
 */


@Configuration
@EnableMongoRepositories(mongoTemplateRef = "mongoTemplate",
        basePackages = {"com.zeroexception.dataConfiguration"},
        createIndexesForQueryMethods = true
)
public interface TokenTrackerRepository extends MongoRepository<TokenTracker, Long> {

    TokenTracker findOneByToken(String token);

    TokenTracker findOneByUserEmail(String email);

    void deleteAllByExpireAtEsBefore(long epochSecond);

    void deleteTokenTrackerByUserEmail(String email);

    void deleteTokenTrackerByToken(String token);
}
