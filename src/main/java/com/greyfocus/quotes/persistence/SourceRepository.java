package com.greyfocus.quotes.persistence;

import com.greyfocus.quotes.model.Source;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository for quote sources.
 */
@RepositoryRestResource(collectionResourceRel = "sources", path = "sources")
public interface SourceRepository extends MongoRepository<Source, String> {

    /**
     * Find a source by name.
     *
     * @param name the name of the source.
     * @return the source.
     */
    Source findByName(String name);
}
