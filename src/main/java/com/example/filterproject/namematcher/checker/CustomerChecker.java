package com.example.filterproject.namematcher.checker;

import com.example.filterproject.namematcher.model.CheckResult;
import com.example.filterproject.namematcher.model.NormilizedCustomerData;
import com.example.filterproject.namematcher.model.RiskCustomer;

import java.util.List;
import java.util.Map;

public interface CustomerChecker {

    CheckResult calculate(List<NormilizedCustomerData> dbMatchCustomerList, NormilizedCustomerData customerToCheck);

    CheckResult calculate(NormilizedCustomerData dbMatchCustomer, NormilizedCustomerData customerToCheck);

    Double checkAddressGroup(NormilizedCustomerData dbMatchCustomer, NormilizedCustomerData customerToCheck);

    Double checkNameGroup(NormilizedCustomerData dbMatchCustomer, NormilizedCustomerData customerToCheck);

    Double getMiddleResult(Map<String, Object> foundCustomerMap, Map.Entry<String, Object> inputEntrySet);

//    Double getTotalThreshold();
//
//    Double getAddressThreshold();
}
