package com.example.filterproject.namematcher.service;

import com.example.filterproject.namematcher.checker.CustomerChecker;
import com.example.filterproject.namematcher.formatter.TextFormatter;
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

    private final TextFormatter textFormatter;
    private final CustomerChecker dynamicCheckerImpl;
    private final CustomerService customerService;

    public NameMatcherService(CustomerChecker dynamicCheckerImpl,
                              TextFormatter textFormatter,
                              CustomerService customerService) {
        this.dynamicCheckerImpl = dynamicCheckerImpl;
        this.textFormatter = textFormatter;
        this.customerService = customerService;
    }

    public ServiceDescision process(RiskCustomer inputCustomer) {
        inputCustomer = textFormatter.process(inputCustomer);
        ServiceDescision serviceDescision = ServiceDescision.builder().
                descisionName(DescisionName.NEGATIVE)
                .build();
        List<RiskCustomer> matchList = customerService.findSimilarCustomers(inputCustomer);
        log.info("[DYNAMIC-CHECKER] - Found {} possible matches", matchList.size());
        if (matchList.size() > 0) {
            CheckResult checkResult = dynamicCheckerImpl.apply(matchList, inputCustomer);
            return compareToThreshold(checkResult);
        }
        return serviceDescision;
    }

    private ServiceDescision compareToThreshold(CheckResult checkResult) {
        Double totalThreshold = dynamicCheckerImpl.getTotalThreshold();
        Double addressThreshold = dynamicCheckerImpl.getAddressThreshold();
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
