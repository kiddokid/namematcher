package com.example.filterproject.namematcher.tester;

import com.example.filterproject.namematcher.checker.configuration.ApacheNGramDistance;
import com.example.filterproject.namematcher.dao.RiskCustomerRepository;
import com.example.filterproject.namematcher.formatter.TextFormatter;
import com.example.filterproject.namematcher.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Printer {

    private RiskCustomerRepository riskCustomerRepository;
    private CustomerService customerService;
    private LevenshteinDistance levenshteinDistance = new LevenshteinDistance(9);
    private TextFormatter textFormatter;

    private static Integer offset = 0;

    public Printer(RiskCustomerRepository riskCustomerRepository,
                   CustomerService customerService,
                   TextFormatter textFormatter) {
        this.riskCustomerRepository = riskCustomerRepository;
        this.customerService = customerService;
        this.textFormatter = textFormatter;
    }

    @Scheduled(fixedDelay = 10000)
    public void printText() {
        //log.info("NGRAM SIMILARITY TEST {} ", nGramDistance.getDistance("source string", "sorce string"));
    }
}
