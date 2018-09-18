package com.example.filterproject.namematcher.tester;

import com.example.filterproject.namematcher.dao.RiskCustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Printer {

    private RiskCustomerRepository riskCustomerRepository;

    public Printer(RiskCustomerRepository riskCustomerRepository) {
        this.riskCustomerRepository = riskCustomerRepository;
    }


    @Scheduled(fixedDelay = 3000)
    public void printText() {
        Long count = riskCustomerRepository.count();
        log.info("Print data from source {}", count);
    }
}
