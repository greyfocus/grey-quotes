package com.greyfocus.persistence;

import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

/**
 * Custom aggregation operation that extends the aggregation support from Spring Data, in order to take advantage of
 * advanced Mongo features.
 */
public class CustomAggregationOperation implements AggregationOperation {

    private DBObject operation;

    public CustomAggregationOperation(DBObject operation) {
        this.operation = operation;
    }

    @Override
    public DBObject toDBObject(AggregationOperationContext context) {
        return context.getMappedObject(operation);
    }
}
