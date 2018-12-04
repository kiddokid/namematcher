package com.example.filterproject.namematcher.dao;

import com.example.filterproject.namematcher.model.SystemProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemPropertyRepository extends JpaRepository<SystemProperty, String> {

}
