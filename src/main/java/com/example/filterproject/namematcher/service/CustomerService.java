package com.example.filterproject.namematcher.service;

import com.example.filterproject.namematcher.dao.RiskCustomerRepository;
import com.example.filterproject.namematcher.formatter.TextFormatter;
import com.example.filterproject.namematcher.model.RiskCustomer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final RiskCustomerRepository riskCustomerRepository;
    private final TextFormatter textFormatter;

    public CustomerService(RiskCustomerRepository riskCustomerRepository,
                           TextFormatter textFormatter) {
        this.riskCustomerRepository = riskCustomerRepository;
        this.textFormatter = textFormatter;
    }

    public void save(RiskCustomer riskCustomer) {
        //TODO ADD STATE MACHINE BASE STATE
        riskCustomerRepository.save(riskCustomer);
    }

    public RiskCustomer get(Long id) {
        return riskCustomerRepository.getRiskCustomerById(id);
    }
}
