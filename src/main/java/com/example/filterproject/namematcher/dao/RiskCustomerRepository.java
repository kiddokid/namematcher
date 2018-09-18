package com.example.filterproject.namematcher.dao;

import com.example.filterproject.namematcher.model.RiskCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskCustomerRepository extends JpaRepository<RiskCustomer, Long> {
}
