package com.example.filterproject.namematcher.service;

import com.example.filterproject.namematcher.dao.RiskCustomerRepository;
import com.example.filterproject.namematcher.formatter.TextFormatter;
import com.example.filterproject.namematcher.model.RiskCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerService {

    private final RiskCustomerRepository riskCustomerRepository;
    private final TextFormatter textFormatter;

    public CustomerService(RiskCustomerRepository riskCustomerRepository,
                           TextFormatter textFormatter) {
        this.riskCustomerRepository = riskCustomerRepository;
        this.textFormatter = textFormatter;
    }

    public Long save(RiskCustomer riskCustomer) {
        //ADD STATE MACHINE BASE STATE
        return riskCustomerRepository.save(riskCustomer).getId();
    }

    public RiskCustomer get(Long id) {
        return riskCustomerRepository.getRiskCustomerById(id);
    }

    public List<RiskCustomer> findSimilarCustomers(RiskCustomer riskCustomer) {
        return riskCustomerRepository.searchSimilarCustomers(riskCustomer.getFirstName(), riskCustomer.getLastName(),
                riskCustomer.getEmail(), riskCustomer.getAddress1(), riskCustomer.getAddress2(), riskCustomer.getCity(),
                riskCustomer.getRegion_state(), riskCustomer.getZip());
    }


}
