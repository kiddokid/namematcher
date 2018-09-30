package com.example.filterproject.namematcher.checker;

import com.example.filterproject.namematcher.model.CheckResult;
import com.example.filterproject.namematcher.model.RiskCustomer;

import java.util.List;

public interface CustomerChecker {

    CheckResult apply(List<RiskCustomer> dbMatchList, RiskCustomer customerToCheck);

    Double getTotalThreshold();

    Double getAddressThreshold();
}
