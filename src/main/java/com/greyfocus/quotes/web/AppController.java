package com.greyfocus.quotes.web;

import com.greyfocus.quotes.persistence.QuoteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
public class AppController {

    @Inject
    private QuoteRepository quoteRepository;

    @RequestMapping("/")
    public String randomQuote(Model model) {
        // TODO: error handling
        model.addAttribute("quote", quoteRepository.randomQuote());

        return "index";
    }
}
