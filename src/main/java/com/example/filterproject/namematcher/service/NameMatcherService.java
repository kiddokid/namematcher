package com.example.filterproject.namematcher.service;

import com.example.filterproject.namematcher.checker.CustomerChecker;
import com.example.filterproject.namematcher.formatter.TextFormatter;
import com.example.filterproject.namematcher.model.CheckResult;
import com.example.filterproject.namematcher.model.RiskCustomer;
import com.example.filterproject.namematcher.model.descision.DescisionName;
import com.example.filterproject.namematcher.model.descision.ServiceDescision;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.filterproject.namematcher.checker.configuration.CheckerConfiguration.ADDRESS_THRESHOLD;
import static com.example.filterproject.namematcher.checker.configuration.CheckerConfiguration.TOTAL_THRESHOLD;

@Service
@Slf4j
public class NameMatcherService {

    @Autowired
    private TextFormatter textFormatter;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private List<CustomerChecker> customerCheckerList = new ArrayList<>();

    public ServiceDescision process(RiskCustomer inputCustomer) {
        inputCustomer = textFormatter.process(inputCustomer);
        ServiceDescision serviceDescision = ServiceDescision.builder().
                descisionName(DescisionName.NEGATIVE)
                .build();
        List<RiskCustomer> matchList = customerService.findSimilarCustomers(inputCustomer);
        log.info("[DYNAMIC-CHECKER] - Found {} possible matches", matchList.size());
        if (matchList.size() > 0) {
            CheckResult checkResult = calculateAverageOfAllCheckers(matchList, inputCustomer);
            return compareToThreshold(checkResult);
        }
        return serviceDescision;
    }

    private ServiceDescision compareToThreshold(CheckResult checkResult) {
        if (checkResult.getTotalMatch() >= TOTAL_THRESHOLD) {
            return ServiceDescision.builder()
                    .checkResult(checkResult)
                    .descisionName(DescisionName.MATCH)
                    .build();
        } else if (checkResult.getAddressMatch() >= ADDRESS_THRESHOLD) {
            return ServiceDescision.builder()
                    .checkResult(checkResult)
                    .descisionName(DescisionName.LOW_CONFIDENCE)
                    .build();
        }
        return null;
    }

    private CheckResult calculateAverageOfAllCheckers(List<RiskCustomer> matchList, RiskCustomer customerToCheck) {
        CheckResult averageCheckResult = new CheckResult();
        List<CheckResult> allChecksResults = customerCheckerList.stream()
                .map(checker -> checker.calculate(matchList, customerToCheck))
                .collect(Collectors.toList());

        averageCheckResult.setNameMatch(allChecksResults.stream()
                .mapToDouble(CheckResult::getNameMatch)
                .average()
                .getAsDouble());
        averageCheckResult.setAddressMatch(allChecksResults.stream()
                .mapToDouble(CheckResult::getAddressMatch)
                .average()
                .getAsDouble());
        averageCheckResult.setAddressMatch(allChecksResults.stream()
                .mapToDouble(CheckResult::getTotalMatch)
                .average()
                .getAsDouble());
        averageCheckResult.setRiskCustomer(allChecksResults.stream()
                .max(Comparator.comparing(CheckResult::getTotalMatch))
                .get().getRiskCustomer());
        return averageCheckResult;
    }
}
