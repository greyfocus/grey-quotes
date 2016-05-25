package com.greyfocus.quotes.persistence;

import com.greyfocus.quotes.model.Quote;

/**
 * Extended quote repository that adds new methods.
 */
public interface QuoteRepositoryCustom {

    /**
     * Selects a random quote from the repository.
     *
     * @return a random quote.
     */
    Quote randomQuote();

}
