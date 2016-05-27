package com.greyfocus.quotes.persistence;

import com.greyfocus.quotes.model.Quote;
import com.greyfocus.quotes.persistence.random.RandomQuoteStrategy;

import javax.inject.Inject;

/**
 * Default implementation of {@link QuoteRepositoryCustom} .
 */
public class QuoteRepositoryImpl implements QuoteRepositoryCustom {

    private RandomQuoteStrategy randomQuoteStrategy;

    /**
     * Creates a new instance.
     *
     * @param randomQuoteStrategy the random quote strategy to be used.
     */
    @Inject
    public QuoteRepositoryImpl(RandomQuoteStrategy randomQuoteStrategy) {
        this.randomQuoteStrategy = randomQuoteStrategy;
    }

    @Override
    public Quote randomQuote() {
        return randomQuoteStrategy.random();
    }

}
