package com.example.filterproject.namematcher.checker;

import com.example.filterproject.namematcher.model.CheckResult;
import com.example.filterproject.namematcher.model.RiskCustomer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;

import java.util.*;

import static java.util.Objects.nonNull;

@Slf4j
@Data
public class AbstractCustomerSimilarityChecker implements CustomerChecker {

    private Integer notNullCount = 0;
    private Double totalResult = 0.0;
    private Double nameCoeff = 0.0;
    private Double addressCoeff = 0.0;
    private Double totalCoeff = 0.0;

    @Override
    public CheckResult calculate(List<RiskCustomer> dbMatchList, RiskCustomer customerToCheck) {
        List<CheckResult> checkResultList = new ArrayList<>();
        dbMatchList.forEach(foundCustomer -> checkResultList.add(calculate(foundCustomer, customerToCheck)));
        return Collections.max(checkResultList, Comparator.comparing(CheckResult::getTotalMatch));
    }

    @Override
    public CheckResult calculate(RiskCustomer foundCustomer, RiskCustomer inputCustomer) {
        Double addressResult;
        Double nameResult;
        totalResult = 0.0;

        nameCoeff = calculateCoefficient(inputCustomer.getNameMap());
        addressCoeff = calculateCoefficient(inputCustomer.getAddressMap());
        totalCoeff = calculateCoefficient(inputCustomer.getAttributeMap());

        nameResult = checkNameGroup(foundCustomer, inputCustomer);
        addressResult = checkAddressGroup(foundCustomer, inputCustomer);
        checkOthers(foundCustomer, inputCustomer);

        totalResult = Precision.round(totalResult, 2);
        log.info("[{}] - For userId - {} total result is {}", getClass().getSimpleName(), foundCustomer.getId(), totalResult, totalResult);
        return CheckResult.builder()
                .addressMatch(addressResult)
                .riskCustomer(inputCustomer)
                .nameMatch(nameResult)
                .totalMatch(totalResult)
                .build();
    }

    @Override
    public Double checkAddressGroup(RiskCustomer foundCustomer, RiskCustomer inputCustomer) {
        Double result = 0.0;
        Double midResult;
        for (Map.Entry<String, Object> inputEntry : inputCustomer.getAddressMap().entrySet()) {
            if (nonNull(inputEntry.getValue()) && nonNull(foundCustomer.getAddressMap().get(inputEntry.getKey()))) {
                midResult = getMiddleResult(foundCustomer.getAddressMap(), inputEntry);
                result += (midResult * addressCoeff);
            }
        }
        log.info("[{}] - AddressChecking result - {}", getClass().getSimpleName(), result);
        return Precision.round(result, 2);
    }

    @Override
    public Double checkNameGroup(RiskCustomer foundCustomer, RiskCustomer inputCustomer) {
        Double result = 0.0;
        Double midResult;
        for (Map.Entry<String, Object> inputEntry : inputCustomer.getNameMap().entrySet()) {
            if (nonNull(inputEntry.getValue()) && nonNull(foundCustomer.getNameMap().get(inputEntry.getKey()))) {
                midResult = getMiddleResult(foundCustomer.getNameMap(), inputEntry);
                result += (midResult * nameCoeff);
            }
        }
        log.info("[{}] - NameChecking result - {}", getClass().getSimpleName(), result);
        return Precision.round(result, 2);
    }

    private void checkOthers(RiskCustomer foundCustomer, RiskCustomer inputCustomer) {
        for (Map.Entry<String, Object> inputEntry : inputCustomer.getOthersMap().entrySet()) {
            if (nonNull(inputEntry.getValue()) && nonNull(foundCustomer.getOthersMap().get(inputEntry.getKey()))) {
                getMiddleResult(foundCustomer.getOthersMap(), inputEntry);
            }
        }
    }

    protected Double calculateCoefficient(Map<String, Object> customerAttMap) {
        this.notNullCount = 0;
        customerAttMap.entrySet().iterator().forEachRemaining(field -> {
            if (nonNull(field.getValue())) {
                notNullCount++;
            }
        });
        return Precision.round((100.0 / notNullCount), 1);
    }

    public Double getMiddleResult(Map<String, Object> foundCustomerMap, Map.Entry<String, Object> inputEntrySet) {
        return null;
    }
}
