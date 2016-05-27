package com.greyfocus.quotes.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Short form of the {@link Source} object, to be used as a reference when only the name of the source is needed.
 */
@TypeAlias("source_name")
public class SourceName {

    @Field("source_id")
    private String sourceId;

    @NotBlank
    private String name;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SourceName{" +
                "sourceId='" + sourceId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
