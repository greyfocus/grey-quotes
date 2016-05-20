package com.greyfocus.persistence;

import com.greyfocus.model.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Repository that manages the quote repository.
 */
@RepositoryRestResource(collectionResourceRel = "quotes", path = "quotes")
public interface QuoteRepository extends MongoRepository<Quote, String>, QuoteRepositoryCustom {

    /**
     * Finds quotes with the given tags.
     *
     * @param tags the tags.
     * @param pageable the pagination details.
     * @return the paged results.
     */
    Page<Quote> findByTagsIn(@Param("tags") List<String> tags, Pageable pageable);
}
