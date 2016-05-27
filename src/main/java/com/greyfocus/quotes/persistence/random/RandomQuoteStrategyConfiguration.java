package com.greyfocus.quotes.persistence.random;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * Configures the random quote strategy depending on the environment settings.
 */
@Configuration
public class RandomQuoteStrategyConfiguration {

    @ConditionalOnProperty(value = "persistence.randomQuote.strategy", havingValue = "rnd-field")
    @Bean
    public RandomQuoteStrategy rndFieldRandomQuoteStrategy(MongoOperations mongoOperations) {
        return new RndFieldRandomQuoteStrategy(mongoOperations);
    }

    @ConditionalOnMissingBean(RandomQuoteStrategy.class)
    @ConditionalOnExpression("'${persistence.randomQuote.strategy}:sample'=='sample'")
    @Bean
    public RandomQuoteStrategy mongoSampleRandomQuoteStrategy(MongoOperations mongoOperations) {
        return new MongoSampleRandomQuoteStrategy(mongoOperations);
    }

    @ConditionalOnProperty(value = "persistence.randomQuote.strategy", havingValue = "skip")
    @Bean
    public RandomQuoteStrategy skipRandomQuoteStrategy(MongoOperations mongoOperations) {
        return new SkipRandomQuoteStrategy(mongoOperations);
    }
}
