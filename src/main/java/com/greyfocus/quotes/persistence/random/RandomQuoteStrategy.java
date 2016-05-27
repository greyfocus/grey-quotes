package com.greyfocus.quotes.persistence.random;

import com.greyfocus.quotes.model.Quote;

/**
 * Interface for strategies that can provide random quotes from the persistence data store.
 */
public interface RandomQuoteStrategy {

    /**
     * @return a random quote.
     */
    Quote random();
}
