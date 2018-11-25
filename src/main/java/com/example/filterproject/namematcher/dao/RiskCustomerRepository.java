package com.example.filterproject.namematcher.dao;

import com.example.filterproject.namematcher.model.RiskCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RiskCustomerRepository extends JpaRepository<RiskCustomer, Long> {

    RiskCustomer getRiskCustomerById(Long id);

    @Query("from RiskCustomer where lower(firstName) like :f_name or lower(lastName) like :l_name " +
            "or lower(email) like :email or lower(address1) like :address1 or lower(address2) like :address2 " +
            "or lower(city) like :city or lower(region_state) like :state or lower(zip) like :zip ")
    List<RiskCustomer> searchSimilarCustomers(@Param("f_name") String firstName, @Param("l_name") String lastName,
                                              @Param("email") String email, @Param("address1") String address1,
                                              @Param("address2") String address2, @Param("city") String city,
                                              @Param("state") String state, @Param("zip") String zip);

    @Query(value = "select * from namematching.risk_customers limit :batchSize", nativeQuery = true)
    List<RiskCustomer> getCustomersInBatch(@Param("batchSize")Integer batchSize);

}
