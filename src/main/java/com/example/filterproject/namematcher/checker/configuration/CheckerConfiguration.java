package com.example.filterproject.namematcher.checker.configuration;

import com.example.filterproject.namematcher.checker.implementations.NGramSimilarityChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CheckerConfiguration {

    public static final Double TOTAL_THRESHOLD = 40.0;
    public static final Double ADDRESS_THRESHOLD = 40.0;

    @Bean
    @Primary
    public NGramSimilarityChecker TwoGramSimilarityChecker() {
        return new NGramSimilarityChecker(new ApacheNGramDistance(2));
    }

    @Bean
    public NGramSimilarityChecker ThreeGramSimilarityChecker() {
        return new NGramSimilarityChecker(new ApacheNGramDistance(3));
    }
}
