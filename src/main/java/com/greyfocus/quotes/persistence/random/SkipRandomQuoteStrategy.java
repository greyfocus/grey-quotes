package com.greyfocus.quotes.persistence.random;

import com.greyfocus.quotes.model.Quote;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Strategy which chooses a quote by skipping a random number of entries from the list of all quotes. This strategy
 * provides poor performance for larger data sets.
 */
public class SkipRandomQuoteStrategy implements RandomQuoteStrategy {

    private MongoOperations mongoOperations;

    @Inject
    public SkipRandomQuoteStrategy(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Quote random() {
        Query query = new Query();
        int skipCount = ThreadLocalRandom.current().nextInt((int)
                Math.min(mongoOperations.count(query, Quote.class), Integer.MAX_VALUE));

        query.skip(skipCount);
        query.limit(1);

        List<Quote> results = mongoOperations.find(query, Quote.class);
        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }
}
