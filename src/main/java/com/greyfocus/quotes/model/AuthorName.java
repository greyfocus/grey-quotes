package com.greyfocus.quotes.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Short version of {@link Author}, to be used as a reference when only the ID and author name are required.
 */
@TypeAlias("author_name")
public class AuthorName {

    @Field("author_id")
    private String authorId;

    @NotBlank
    private String name;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorName{" +
                "authorId='" + authorId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
