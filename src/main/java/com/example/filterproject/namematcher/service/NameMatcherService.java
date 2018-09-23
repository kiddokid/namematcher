package com.example.filterproject.namematcher.service;

import com.example.filterproject.namematcher.checker.CustomerChecker;
import com.example.filterproject.namematcher.checker.DynamicCheckerImpl;
import com.example.filterproject.namematcher.dao.RiskCustomerRepository;
import com.example.filterproject.namematcher.model.CheckResult;
import com.example.filterproject.namematcher.model.RiskCustomer;
import com.example.filterproject.namematcher.model.descision.DescisionName;
import com.example.filterproject.namematcher.model.descision.ServiceDescision;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NameMatcherService {

    private Double totalThreshold = 80.0;
    private Double addressThreshold = 80.0;

    private final CustomerChecker dynamicCheckerImpl;
    private final RiskCustomerRepository riskCustomerRepository;


    public NameMatcherService(CustomerChecker dynamicCheckerImpl,
                              RiskCustomerRepository riskCustomerRepository) {
        this.dynamicCheckerImpl = dynamicCheckerImpl;
        this.riskCustomerRepository = riskCustomerRepository;
    }

    public ServiceDescision process(RiskCustomer inputCustomer) {
        ServiceDescision serviceDescision = ServiceDescision.builder().
                descisionName(DescisionName.NEGATIVE)
                .build();
        List<RiskCustomer> matchList = riskCustomerRepository.searchSimilarCustomers(inputCustomer.getFirstName(), inputCustomer.getLastName(),
                inputCustomer.getEmail(), inputCustomer.getAddress1(), inputCustomer.getAddress2(),
                inputCustomer.getCity(), inputCustomer.getRegion_state(), inputCustomer.getZip());

        if (matchList.size() > 0) {
            CheckResult checkResult = dynamicCheckerImpl.apply(matchList, inputCustomer);
            return compareToThreshold(checkResult);
        }
        return serviceDescision;
    }

    private ServiceDescision compareToThreshold(CheckResult checkResult) {
        if (checkResult.getTotalMatch() >= totalThreshold) {
            return ServiceDescision.builder()
                    .checkResult(checkResult)
                    .descisionName(DescisionName.MATCH)
                    .build();
        } else if (checkResult.getAddressMatch() >= addressThreshold) {
            return ServiceDescision.builder()
                    .checkResult(checkResult)
                    .descisionName(DescisionName.LOW_CONFIDENCE)
                    .build();
        }
        return null;
    }
}
