package com.greyfocus.persistence;

import com.greyfocus.persistence.events.AuthorSaveEventListener;
import com.greyfocus.persistence.events.QuoteSaveEventListener;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuration for the MongoDB database.
 */
@Configuration
@EnableMongoRepositories("com.greyfocus.persistence")
@Import(value = MongoAutoConfiguration.class)
public class DatabaseConfiguration extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private MongoClientURI mongoURI;

    @Bean
    public QuoteSaveEventListener quoteSaveEventListener() {
        return new QuoteSaveEventListener();
    }

    @Bean
    public AuthorSaveEventListener authorSaveEventListener() {
        return new AuthorSaveEventListener();
    }

    /**
     * Register the persistence level validation event listener.
     *
     * @return the validating event listener.
     */
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    protected String getDatabaseName() {
        return mongoURI.getDatabase();
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(mongoURI);
    }
}
