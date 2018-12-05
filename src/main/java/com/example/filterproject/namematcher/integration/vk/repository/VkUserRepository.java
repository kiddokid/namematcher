package com.example.filterproject.namematcher.integration.vk.repository;

import com.example.filterproject.namematcher.integration.vk.model.VkUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VkUserRepository extends JpaRepository<VkUser, Long> {


}
