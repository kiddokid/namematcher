package com.example.filterproject.namematcher.checker;

import com.example.filterproject.namematcher.model.CheckResult;
import com.example.filterproject.namematcher.model.RiskCustomer;

import java.util.List;
import java.util.Map;

public interface CustomerChecker {

    CheckResult calculate(List<RiskCustomer> dbMatchCustomerList, RiskCustomer customerToCheck);

    CheckResult calculate(RiskCustomer dbMatchCustomer, RiskCustomer customerToCheck);

    Double checkAddressGroup(RiskCustomer dbMatchCustomer, RiskCustomer customerToCheck);

    Double checkNameGroup(RiskCustomer dbMatchCustomer, RiskCustomer customerToCheck);

    Double getMiddleResult(Map<String, Object> foundCustomerMap, Map.Entry<String, Object> inputEntrySet);

//    Double getTotalThreshold();
//
//    Double getAddressThreshold();
}
