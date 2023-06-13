package com.trip.server.configuration;

import org.apache.commons.text.similarity.FuzzyScore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class SearcherConfiguration {

    @Bean
    public FuzzyScore fuzzyScore() {
        return new FuzzyScore(new Locale("ru"));
    }

}