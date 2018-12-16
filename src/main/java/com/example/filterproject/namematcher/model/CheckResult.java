package com.example.filterproject.namematcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckResult {

    private RiskCustomer riskCustomer;
    private Double totalMatch;
    private Double addressMatch;
    private Double nameMatch;
}
