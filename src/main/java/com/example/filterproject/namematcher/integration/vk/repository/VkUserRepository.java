package com.example.filterproject.namematcher.integration.vk.repository;

import com.example.filterproject.namematcher.integration.vk.model.VkUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VkUserRepository extends JpaRepository<VkUser, Long> {

    @Query(value = "select * from vkuser where possibleids contains  ", nativeQuery = true)
    Integer getHitInPossibleIds(Integer userId);
}
