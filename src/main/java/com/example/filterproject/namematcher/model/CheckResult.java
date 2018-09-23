package com.example.filterproject.namematcher.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckResult {

    private RiskCustomer riskCustomer;
    private Double totalMatch;
    private Double addressMatch;
    private Double nameMatch;

}
