package com.greyfocus.quotes.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

@TypeAlias("author_name")
public class AuthorName {

    @Field("author_id")
    private String authorId;

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
