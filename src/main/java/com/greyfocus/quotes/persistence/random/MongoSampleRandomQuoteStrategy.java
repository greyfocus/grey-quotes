package com.greyfocus.quotes.persistence.random;

import com.greyfocus.quotes.model.Quote;
import com.mongodb.BasicDBObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import javax.inject.Inject;
import java.util.List;

/**
 * Generates a random quote using the Mongo "sample" aggregation.
 * <br/>
 * This implementation appears to be broken since Spring Boot 1.4.0.
 */
public class MongoSampleRandomQuoteStrategy implements RandomQuoteStrategy {

    private MongoOperations mongoOperations;

    @Inject
    public MongoSampleRandomQuoteStrategy(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Quote random() {
        Aggregation aggregation = Aggregation.newAggregation(Quote.class,
                new CustomAggregationOperation(new BasicDBObject("$sample", new BasicDBObject("size", 1))));

        AggregationResults<Quote> aggregationResults = mongoOperations.aggregate(aggregation, Quote.class, Quote.class);
        List<Quote> results = aggregationResults.getMappedResults();

        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }
}
