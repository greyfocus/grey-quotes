package com.greyfocus.persistence;

import com.greyfocus.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository for author entities.
 */
@RepositoryRestResource(collectionResourceRel = "authors", path = "authors")
public interface AuthorRepository extends MongoRepository<Author, String> {

    /**
     * Finds an author by name.
     *
     * @param name the name of the author.
     * @return the author.
     */
    Author findByName(String name);
}
