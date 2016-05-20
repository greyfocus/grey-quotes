package com.greyfocus.persistence.events;

import com.greyfocus.model.Author;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Listener for Author convert/save events in order to generate a custom ID, based on the author name.
 */
public class AuthorSaveEventListener extends AbstractMongoEventListener<Author> {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Author> event) {
        Author author = event.getSource();

        if (StringUtils.isEmpty(author.getId())) {
            generateId(author);
        }
    }

    private void generateId(Author author) {
        Assert.notNull(author.getName());

        String id = author.getName().toLowerCase().replaceAll("\\s+", "_").replaceAll("[^a-zA-Z_]+", "");
        author.setId(id);
    }
}
