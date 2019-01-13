package com.example.filterproject.namematcher.service;

import com.example.filterproject.namematcher.dao.SystemPropertyRepository;
import com.example.filterproject.namematcher.model.SystemProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class SystemPropertyService {

    private final SystemPropertyRepository repository;

    public SystemPropertyService(SystemPropertyRepository repository) {
        this.repository = repository;
    }

    public Long getLongValue(String key) {
        Long result = null;
        Optional<SystemProperty> systemProperty = repository.findById(key);
        if (systemProperty.isPresent()) {
            try {
                result = Long.valueOf(systemProperty.get().getValue());
            }
            catch (Exception e) {
                log.error("[{}] Invalid property value argument! Can not return Long from String!", this.getClass().getSimpleName());
                return result;
            }
        }
        return result;
    }

    public String getValue(String key) {
        Optional<SystemProperty> systemProperty = repository.findById(key);
        return systemProperty.map(SystemProperty::getValue).orElse(null);
    }

    public SystemProperty save(SystemProperty systemProperty) {
        return repository.save(systemProperty);
    }


}
