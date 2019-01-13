package com.example.filterproject.namematcher.service;

import com.example.filterproject.namematcher.model.RiskCustomer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailMatcherService {

    public Boolean match(List<RiskCustomer> emailsToMatch, String inputEmail) {
        return emailsToMatch.stream().anyMatch(customer -> customer.getEmail().equals(inputEmail));
    }
}
