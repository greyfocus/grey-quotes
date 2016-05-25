package com.greyfocus.quotes.persistence.events;

import com.greyfocus.quotes.model.Author;
import com.greyfocus.quotes.model.AuthorName;
import com.greyfocus.quotes.model.Quote;
import com.greyfocus.quotes.persistence.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.inject.Inject;

/**
 * Listener for quote save/convert events which links the quote with a corresponding author entity. If no such author
 * entity is found, a new one is created and linked with the quote.
 */
public class QuoteSaveEventListener extends AbstractMongoEventListener<Quote> {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteSaveEventListener.class);

    @Inject
    private AuthorRepository authorRepository;

    @Inject
    private MongoConverter mongoConverter;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Quote> event) {
        Quote quote = event.getSource();
        updateAuthor(quote);
        LOGGER.info("Updated quote " + quote);
    }

    /**
     * Ensures that the author field on the quote is set correctly.
     * <ul>
     *     <li>it creates a new author and assigns the author_id property if no author with the given name exists.</li>
     *     <li>it sets the author_id field property if the author already exists.</li>
     * </ul>
     * @param quote the quote
     * @return <code>true</code> if the author was altered, <code>false</code> if no changes occurred.
     */
    private boolean updateAuthor(Quote quote) {
        AuthorName authorName = quote.getAuthor();
        Assert.notNull(authorName);

        if (!StringUtils.isEmpty(authorName.getAuthorId())) {
            // Nothing to do in this case since we already have an author ID.
            return false;
        }

        Author author = authorRepository.findByName(authorName.getName());
        if (author == null) {
            author = new Author();
            author.setName(authorName.getName());

            authorRepository.save(author);
        }

        authorName.setAuthorId(author.getId());

        return true;
    }
}
