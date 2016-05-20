package com.greyfocus.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

/**
 * Model class for quotes.
 */
@TypeAlias("quote")
public class Quote {

    @Id
    private String id;

    private Instant createdAt = Instant.now();

    @NotBlank(message = "The text cannot be empty.")
    private String text;

    @Indexed
    @NotNull(message = "Author cannot be empty.")
    private AuthorName author;

    @Indexed
    private List<String> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AuthorName getAuthor() {
        return author;
    }

    public void setAuthor(AuthorName author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", tags=" + tags +
                '}';
    }
}
