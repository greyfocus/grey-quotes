package com.greyfocus.quotes.web;

import com.greyfocus.quotes.model.Quote;
import com.greyfocus.quotes.persistence.QuoteRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
public class AppController {

    @Inject
    private QuoteRepository quoteRepository;

    @RequestMapping("/")
    public String randomQuote(Model model) {
        Quote randomQuote = quoteRepository.randomQuote();
        if (randomQuote == null) {
            throw new ResourceNotFoundException("No quote was found.");
        }

        model.addAttribute("quote", randomQuote);

        return "index";
    }

    @RequestMapping("/quotes/{quoteId}")
    public String quoteById(@PathVariable("quoteId") String quoteId, Model model) {
        Quote quote = quoteRepository.findOne(quoteId);
        if (quote == null) {
            throw new ResourceNotFoundException("No quote was found.");
        }

        model.addAttribute("quote", quote);

        return "index";
    }
}
