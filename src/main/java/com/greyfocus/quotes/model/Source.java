package com.greyfocus.quotes.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

/**
 * Models a quotation source.
 */
@TypeAlias("source")
public class Source {

    @Id
    private String id;

    @NotEmpty
    private List<AuthorName> authors;

    @Indexed
    @NotBlank
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AuthorName> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorName> authors) {
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Source{" +
                "id='" + id + '\'' +
                ", authors=" + authors +
                ", name='" + name + '\'' +
                '}';
    }
}
