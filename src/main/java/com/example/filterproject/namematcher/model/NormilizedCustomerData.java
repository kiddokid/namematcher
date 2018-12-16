package com.example.filterproject.namematcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(schema = "namematching")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormilizedCustomerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long riskCustomerId;
    private String name;
    private String address1;
    private String address2;
    private String region;
    private String city;
    private String zip;
    private String country;
}
