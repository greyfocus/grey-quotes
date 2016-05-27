package com.greyfocus.quotes.persistence.random;

import com.greyfocus.quotes.model.Quote;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.inject.Inject;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Fetches a random quote from the database, by using the "rnd" column algorithm. This strategy provides very poor
 * distribution: quotes with smaller {code}rnd{code} value are less likely to be chosen.
 */
public class RndFieldRandomQuoteStrategy implements RandomQuoteStrategy {

    private MongoOperations mongoOperations;

    @Inject
    public RndFieldRandomQuoteStrategy(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Quote random() {
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

}
