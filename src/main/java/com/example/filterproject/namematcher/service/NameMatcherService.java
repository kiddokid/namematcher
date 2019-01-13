package com.example.filterproject.namematcher.service;

import com.example.filterproject.namematcher.checker.CustomerChecker;
import com.example.filterproject.namematcher.dao.NormilizedCustomerDataRepository;
import com.example.filterproject.namematcher.dao.RiskCustomerRepository;
import com.example.filterproject.namematcher.formatter.TextFormatter;
import com.example.filterproject.namematcher.model.CheckResult;
import com.example.filterproject.namematcher.model.NormilizedCustomerData;
import com.example.filterproject.namematcher.model.RiskCustomer;
import com.example.filterproject.namematcher.model.descision.DescisionName;
import com.example.filterproject.namematcher.model.descision.ServiceDescision;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.filterproject.namematcher.checker.configuration.CheckerConfiguration.ADDRESS_THRESHOLD;
import static com.example.filterproject.namematcher.checker.configuration.CheckerConfiguration.TOTAL_THRESHOLD;

@Service
@Slf4j
public class NameMatcherService {

    private TextFormatter textFormatter;
    private CustomerService customerService;
    private NormilizedCustomerDataRepository normilizedCustomerDataRepository;
    private RiskCustomerRepository riskCustomerRepository;
    private List<CustomerChecker> customerCheckerList;
    private EmailMatcherService emailMatcherService;

    public NameMatcherService(TextFormatter textFormatter,
                              CustomerService customerService,
                              NormilizedCustomerDataRepository normilizedCustomerDataRepository,
                              RiskCustomerRepository riskCustomerRepository,
                              List<CustomerChecker> customerCheckerList,
                              EmailMatcherService emailMatcherService) {
        this.textFormatter = textFormatter;
        this.customerService = customerService;
        this.normilizedCustomerDataRepository = normilizedCustomerDataRepository;
        this.riskCustomerRepository = riskCustomerRepository;
        this.customerCheckerList = customerCheckerList;
        this.emailMatcherService = emailMatcherService;
    }

    public ServiceDescision process(RiskCustomer inputCustomer) {
        Boolean isEmailMatch;
        inputCustomer = textFormatter.process(inputCustomer);
        NormilizedCustomerData normalizedInputCustomer = textFormatter.normalize(inputCustomer);
        ServiceDescision serviceDescision = ServiceDescision.builder()
                .descisionName(DescisionName.NEGATIVE)
                .build();
        List<RiskCustomer> foundRiskCustomers = customerService.findSimilarCustomers(inputCustomer);
        List<NormilizedCustomerData> matchList = normilizedCustomerDataRepository.findAllByRiskCustomerIdIn(
                foundRiskCustomers.stream()
                        .map(RiskCustomer::getId)
                        .collect(Collectors.toList()));
        isEmailMatch = emailMatcherService.match(foundRiskCustomers, inputCustomer.getEmail());
        log.info("[DYNAMIC-CHECKER] - Found {} possible matches", matchList.size());
        if (matchList.size() > 0) {
            CheckResult checkResult = calculateAverageOfAllCheckers(matchList, normalizedInputCustomer);
            checkResult.setEmailMatch(isEmailMatch);
            return compareToThreshold(checkResult);
        }
        return serviceDescision;
    }

    private ServiceDescision compareToThreshold(CheckResult checkResult) {
        if (checkResult.getTotalMatch() >= TOTAL_THRESHOLD || checkResult.getEmailMatch()) {
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

    private CheckResult calculateAverageOfAllCheckers(List<NormilizedCustomerData> matchList, NormilizedCustomerData customerToCheck) {
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
        averageCheckResult.setTotalMatch(allChecksResults.stream()
                .mapToDouble(CheckResult::getTotalMatch)
                .average()
                .getAsDouble());
//        averageCheckResult.setRiskCustomer(allChecksResults.stream()
//                .max(Comparator.comparing(CheckResult::getTotalMatch))
//                .get()
//                .getRiskCustomer());
        return averageCheckResult;
    }
}
