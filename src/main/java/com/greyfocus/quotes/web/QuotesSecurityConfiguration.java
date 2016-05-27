package com.greyfocus.quotes.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Security configuration for the main application.
 */
@EnableWebSecurity
@Configuration
public class QuotesSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/api").fullyAuthenticated()
                    .antMatchers(HttpMethod.GET, "/api/quotes").permitAll()
                .and().httpBasic();
    }
}
