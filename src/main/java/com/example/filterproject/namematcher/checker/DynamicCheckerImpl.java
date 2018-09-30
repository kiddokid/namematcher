package com.example.filterproject.namematcher.checker;

import com.example.filterproject.namematcher.model.CheckResult;
import com.example.filterproject.namematcher.model.RiskCustomer;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.Objects.nonNull;

@Component
@Slf4j
@Getter
public class DynamicCheckerImpl implements CustomerChecker {

    private Double totalThreshold = 80.0;
    private Double addressThreshold = 80.0;

    private Integer notNullCount = 0;
    private Double totalResult = 0.0;
    private Double nameCoeff = 0.0;
    private Double addressCoeff = 0.0;
    private Double totalCoeff = 0.0;

    private JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();

    @Override
    public CheckResult apply(List<RiskCustomer> dbMatchList, RiskCustomer customerToCheck) {
        List<CheckResult> checkResultList = new ArrayList<>();
        dbMatchList.forEach(foundCustomer -> checkResultList.add(apply(foundCustomer, customerToCheck)));
        return Collections.max(checkResultList, Comparator.comparing(CheckResult::getTotalMatch));
    }

    protected CheckResult apply(RiskCustomer foundCustomer, RiskCustomer inputCustomer) {
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
        log.info("[DYNAMIC-CHECKER] - For userId - {} total result is {}", foundCustomer.getId(), totalResult, totalResult);
        return CheckResult.builder()
                .addressMatch(addressResult)
                .riskCustomer(inputCustomer)
                .nameMatch(nameResult)
                .totalMatch(totalResult)
                .build();

    }

    protected Double checkAddressGroup(RiskCustomer foundCustomer, RiskCustomer inputCustomer) {
        Double result = 0.0;
        Double midResult;
        for (Map.Entry<String, Object> inputEntry : inputCustomer.getAddressMap().entrySet()) {
            if (nonNull(inputEntry.getValue()) && nonNull(foundCustomer.getAddressMap().get(inputEntry.getKey()))) {
                midResult = getMiddleResult(foundCustomer.getAddressMap(), inputEntry);
                result += (midResult * addressCoeff);
            }
        }
        log.info("[DYNAMIC-CHECKER] - AddressChecking result - {}", result);
        return Precision.round(result, 2);
    }


    protected Double checkNameGroup(RiskCustomer foundCustomer, RiskCustomer inputCustomer) {
        Double result = 0.0;
        Double midResult;
        for (Map.Entry<String, Object> inputEntry : inputCustomer.getNameMap().entrySet()) {
            if (nonNull(inputEntry.getValue()) && nonNull(foundCustomer.getNameMap().get(inputEntry.getKey()))) {
                midResult = getMiddleResult(foundCustomer.getNameMap(), inputEntry);
                result += (midResult * nameCoeff);
            }
        }
        log.info("[DYNAMIC-CHECKER] - NameChecking result - {}", result);
        return Precision.round(result, 2);
    }

    private void checkOthers(RiskCustomer foundCustomer, RiskCustomer inputCustomer) {
        for (Map.Entry<String, Object> inputEntry : inputCustomer.getOthersMap().entrySet()) {
            if (nonNull(inputEntry.getValue()) && nonNull(foundCustomer.getOthersMap().get(inputEntry.getKey()))) {
                getMiddleResult(foundCustomer.getOthersMap(), inputEntry);
            }
        }
    }

    private Double getMiddleResult(Map<String, Object> foundCustomerMap, Map.Entry<String, Object> inputEntrySet) {
        Double midResult = jaroWinklerDistance.apply(inputEntrySet.getValue().toString().toLowerCase(),
                foundCustomerMap.get(inputEntrySet.getKey()).toString().toLowerCase());
        totalResult += (midResult * totalCoeff);
        return midResult;
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



}
