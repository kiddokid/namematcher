package com.example.filterproject.namematcher.dao;

import com.example.filterproject.namematcher.model.SystemProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemPropertyRepository extends JpaRepository<SystemProperty, String> {

    SystemProperty findByProperty(String property);
}
