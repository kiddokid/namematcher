package com.example.filterproject.namematcher.checker.implementations;

import com.example.filterproject.namematcher.checker.AbstractCustomerSimilarityChecker;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@Getter
public class JaroWinklerSimilarityCheckerImpl extends AbstractCustomerSimilarityChecker {

    private JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();

    @Override
    public Double getMiddleResult(Map<String, Object> foundCustomerMap, Map.Entry<String, Object> inputEntrySet) {
        Double midResult = jaroWinklerDistance.apply(inputEntrySet.getValue().toString(),
                foundCustomerMap.get(inputEntrySet.getKey()).toString());
        this.setTotalResult(this.getTotalResult() + (midResult * this.getTotalCoeff()));
        return midResult;
    }





}
