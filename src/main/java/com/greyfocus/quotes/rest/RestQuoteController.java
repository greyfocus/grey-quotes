package com.greyfocus.quotes.rest;

import com.greyfocus.quotes.model.Quote;
import com.greyfocus.quotes.persistence.QuoteRepository;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * Controller which returns a random quote from the repository.
 */
@BasePathAwareController
@RequestMapping("/quotes/search")
@ExposesResourceFor(Quote.class)
public class RestQuoteController implements ResourceProcessor<RepositorySearchesResource>, ResourceAssembler<Quote, Resource<Quote>> {

    @Inject
    private QuoteRepository quoteRepository;

    @Inject
    private EntityLinks entityLinks;

    @Inject
    private RepositoryEntityLinks repositoryEntityLinks;

    @RequestMapping(value = "random", method = RequestMethod.GET)
    public ResponseEntity<Resource<Quote>> randomQuote() {
        Quote quote = quoteRepository.randomQuote();
        if (quote == null) {
            throw new ResourceNotFoundException("No quote was found");
        }
        return new ResponseEntity<>(toResource(quote), HttpStatus.OK);
    }

    @Override
    public Resource<Quote> toResource(Quote quote) {
        Link quoteLink = repositoryEntityLinks.linkToSingleResource(Quote.class, quote.getId());
        Link selfLink = new Link(quoteLink.getHref(), Link.REL_SELF);

        return new Resource<>(quote, Arrays.asList(quoteLink, selfLink));
    }

    @Override
    public RepositorySearchesResource process(RepositorySearchesResource resource) {
        LinkBuilder linkBuilder = entityLinks.linkFor(Quote.class, "name");
        resource.add(new Link(linkBuilder.toString() + "/search/random", "random"));
        return resource;
    }
}
