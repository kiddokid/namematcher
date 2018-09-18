package com.example.filterproject.namematcher.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "risk_persons")
@Data
public class RiskCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rp_first_name")
    @NotNull(message = "is required")
    private String firstName;

    @Column(name = "rp_middle_name")
    private String middleName;

    @Column(name = "rp_last_name")
    @NotNull(message = "is required")
    private String lastName;

    @Column(name = "rp_address_1")
    @NotNull(message = "is required")
    private String address1;

    @Column(name = "rp_address_2")
    private String address2;

    @Column(name = "rp_region_state")
    private String region_state;

    @Column(name = "rp_city")
    private String city;

    @Column(name = "rp_zip_index")
    @NotNull(message = "is required")
    private String zip;

    @Column(name = "rp_country")
    @NotNull(message = "is required")
    private String country;

    @Column(name = "rp_reason")
    @NotNull(message = "is required")
    private String reason;

    @Column(name = "rp_comment")
    private String comment;

    @Column(name = "rp_email")
    @Email(message = "Invalid Email")
    @NotNull(message = "is required")
    private String email;

    @Column(name = "rp_date")
    private Date timestamp;

    @Column(name = "rp_added_by_id")
    @JsonIgnore
    private Long client;
}
