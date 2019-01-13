package com.example.filterproject.namematcher.model.descision;

import com.example.filterproject.namematcher.model.CheckResult;
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
