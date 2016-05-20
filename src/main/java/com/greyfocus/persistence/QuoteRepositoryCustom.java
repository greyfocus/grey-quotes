package com.greyfocus.persistence;

import com.greyfocus.model.Quote;

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
