package com.greyfocus.quotes.persistence;

import com.greyfocus.quotes.model.Quote;
import com.mongodb.BasicDBObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Default implementation of {@link QuoteRepositoryCustom} .
 */
public class QuoteRepositoryImpl implements QuoteRepositoryCustom {

    @Inject
    private MongoOperations mongoOperations;

    @Override
    public Quote randomQuote() {
        double rnd = ThreadLocalRandom.current().nextDouble() * getMaxRndValue();

        Query query = new Query()
                .addCriteria(Criteria.where("rnd").gte(rnd))
                .with(new Sort(Sort.Direction.ASC, "rnd"))
                .limit(1);

        return mongoOperations.findOne(query, Quote.class);
    }

    /**
     * Fetches the max "rnd" value that exists in the collection.
     *
     * @return the value or "0" if no value could be determined (the collection is most likely empty)
     */
    private double getMaxRndValue() {
        Query query = new Query()
                .with(new Sort(Sort.Direction.DESC, "rnd"))
                .limit(1);

        Quote quoteWithHighestRnd = mongoOperations.findOne(query, Quote.class);
        return quoteWithHighestRnd != null ? quoteWithHighestRnd.getRnd() : 0;
    }

    public Quote randomQuote_Mongo32() {
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
