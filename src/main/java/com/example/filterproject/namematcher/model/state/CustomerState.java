package com.example.filterproject.namematcher.model.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Enumerated;

@Data
@Builder
@AllArgsConstructor
public class CustomerState {

    private Long customerId;

    @Enumerated
    private CustomerStateName state;
}
