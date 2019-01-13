package com.example.filterproject.namematcher.checker.implementations;

import com.example.filterproject.namematcher.checker.AbstractCustomerSimilarityChecker;
import com.example.filterproject.namematcher.checker.configuration.ApacheNGramDistance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
public class NGramSimilarityChecker extends AbstractCustomerSimilarityChecker {

    private ApacheNGramDistance nGramDistance;

    public NGramSimilarityChecker(ApacheNGramDistance nGramDistance) {
        this.nGramDistance = nGramDistance;
    }

    @Override
    public Double getMiddleResult(Map<String, Object> foundCustomerMap, Map.Entry<String, Object> inputEntrySet) {
        Float midResult = nGramDistance.getDistance(inputEntrySet.getValue().toString().toLowerCase(),
                foundCustomerMap.get(inputEntrySet.getKey()).toString().toLowerCase());
        this.setTotalResult(this.getTotalResult() + (midResult * this.getTotalCoeff()));
        return Double.valueOf(midResult);
    }
}
