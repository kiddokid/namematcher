package com.example.filterproject.namematcher.dao;

import com.example.filterproject.namematcher.model.NormilizedCustomerData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormilizedCustomerDataRepository extends JpaRepository<NormilizedCustomerData, Long> {

    NormilizedCustomerData findByRiskCustomerId(Long id);
}
