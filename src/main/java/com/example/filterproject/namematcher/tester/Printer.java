package com.example.filterproject.namematcher.tester;

import com.example.filterproject.namematcher.dao.RiskCustomerRepository;
import com.example.filterproject.namematcher.formatter.TextFormatter;
import com.example.filterproject.namematcher.integration.vk.service.VkAliasService;
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
    private VkAliasService vkTest;

    private static Integer offset = 0;

    public Printer(RiskCustomerRepository riskCustomerRepository,
                   CustomerService customerService,
                   TextFormatter textFormatter,
                   VkAliasService vkTest) {
        this.riskCustomerRepository = riskCustomerRepository;
        this.customerService = customerService;
        this.textFormatter = textFormatter;
        this.vkTest = vkTest;
    }


    public void printText() {
       vkTest.process();
    }
}
