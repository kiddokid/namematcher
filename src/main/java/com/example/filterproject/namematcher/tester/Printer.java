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

    }
}
