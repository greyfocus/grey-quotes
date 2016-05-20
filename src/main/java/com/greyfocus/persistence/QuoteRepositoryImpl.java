package com.greyfocus.persistence;

import com.greyfocus.model.Quote;
import com.mongodb.BasicDBObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import javax.inject.Inject;
import java.util.List;

/**
 * Default implementation of {@link QuoteRepositoryCustom} .
 */
public class QuoteRepositoryImpl implements QuoteRepositoryCustom {

    @Inject
    private MongoOperations mongoOperations;

    @Override
    public Quote randomQuote() {
        Aggregation aggregation = Aggregation.newAggregation(
                new CustomAggregationOperation(new BasicDBObject("$sample", new BasicDBObject("size", 1)))
        );

        AggregationResults<Quote> aggregationResults = mongoOperations.aggregate(aggregation, Quote.class, Quote.class);
        List<Quote> results = aggregationResults.getMappedResults();

        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }
}
