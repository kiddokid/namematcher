package com.example.filterproject.namematcher.model.descision;

import com.example.filterproject.namematcher.model.CheckResult;
import com.example.filterproject.namematcher.model.RiskCustomer;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Enumerated;

@Data
@Builder
public class ServiceDescision {

    @Enumerated
    private DescisionName descisionName;
    private CheckResult checkResult;
}
