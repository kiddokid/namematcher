package com.example.filterproject.namematcher.tester;

import com.example.filterproject.namematcher.dao.RiskCustomerRepository;
import com.example.filterproject.namematcher.model.RiskCustomer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class Printer {

    private RiskCustomerRepository riskCustomerRepository;

    private LevenshteinDistance levenshteinDistance = new LevenshteinDistance(9);

    public Printer(RiskCustomerRepository riskCustomerRepository) {
        this.riskCustomerRepository = riskCustomerRepository;
    }


    @Scheduled(fixedDelay = 3000)
    public void printText() {
//        Long count = riskCustomerRepository.count();
//        log.info("Print data from source {}", count);
//        List<RiskCustomer> riskCustomerList = riskCustomerRepository.searchSimilarCustomers("Dorian","Burke",
//                "nbrunicke4@cbc.ca", "802 Darwin Circle", "802 Darwin Circle", "Mount Vernon", "TX", "12222");
//        log.info("Test - {}", riskCustomerList.toString());
//        log.info("Trashgold {}", levenshteinDistance.getThreshold());
//        log.info("Test - {}", levenshteinDistance.apply("bigtest","testbig"));
    }
}
