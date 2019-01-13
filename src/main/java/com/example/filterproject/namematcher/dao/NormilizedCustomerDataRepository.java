package com.example.filterproject.namematcher.dao;

import com.example.filterproject.namematcher.model.NormilizedCustomerData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NormilizedCustomerDataRepository extends JpaRepository<NormilizedCustomerData, Long> {

    NormilizedCustomerData findByRiskCustomerId(Long id);

    List<NormilizedCustomerData> findAllByRiskCustomerIdIn(List<Long> customersIds);
}
