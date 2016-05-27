package com.greyfocus.quotes.persistence.events;

import com.greyfocus.quotes.model.*;
import com.greyfocus.quotes.persistence.AuthorRepository;
import com.greyfocus.quotes.persistence.SourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.Collections;

/**
 * Listener for quote save/convert events which links the quote with a corresponding author entity. If no such author
 * entity is found, a new one is created and linked with the quote.
 */
public class QuoteSaveEventListener extends AbstractMongoEventListener<Quote> {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteSaveEventListener.class);

    @Inject
    private AuthorRepository authorRepository;

    @Inject
    private SourceRepository sourceRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Quote> event) {
        Quote quote = event.getSource();
        updateAuthor(quote);
        updateSource(quote);
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
            updateAuthorName(authorName);

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

    /**
     * Ensures that the source field of the quote is set correctly. This method uses the same algorithm as
     * {@link #updateAuthor(Quote)}, except that it applies to sources.
     * @param quote the quote.
     * @return <code>true</code> if the source was altered, <code>false</code> otherwise.
     */
    private boolean updateSource(Quote quote) {
        SourceName sourceName = quote.getSource();
        if (sourceName == null) {
            return false;
        }

        if (!StringUtils.isEmpty(sourceName.getSourceId())) {
            updateSourceName(sourceName);

            // Nothing to do in this case since we already have a source ID.
            return false;
        }

        Source source = sourceRepository.findByName(sourceName.getName());
        if (source == null) {
            source = new Source();
            source.setName(sourceName.getName());
            source.setAuthors(Collections.singletonList(quote.getAuthor()));

            sourceRepository.save(source);
        }
        sourceName.setSourceId(source.getId());

        return true;
    }

    private void updateAuthorName(AuthorName authorName) {
        Author author = authorRepository.findOne(authorName.getAuthorId());
        if (author == null) {
            throw new ResourceNotFoundException("Unable to find author for ID " + authorName.getAuthorId());
        }

        authorName.setName(author.getName());
    }

    private void updateSourceName(SourceName sourceName) {
        Source source = sourceRepository.findOne(sourceName.getSourceId());
        if (source == null) {
            throw new ResourceNotFoundException("Unable to find source for ID " + sourceName.getSourceId());
        }

        sourceName.setName(source.getName());
    }
}
