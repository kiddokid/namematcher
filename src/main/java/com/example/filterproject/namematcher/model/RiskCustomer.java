package com.example.filterproject.namematcher.model;

import com.example.filterproject.namematcher.model.state.CustomerState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "risk_customers", schema = "namematching")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiskCustomer {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "is required")
    @Column(name = "firstname")
    private String firstName;

    @Column(name = "middlename")
    private String middleName;

    @Column(name = "lastname")
    @NotNull(message = "is required")
    private String lastName;

    @NotNull(message = "is required")
    private String address1;

    private String address2;

    @Column(name = "regionstate")
    private String region_state;

    private String city;

    private String zip;

    @NotNull(message = "is required")
    private String country;

    @NotNull(message = "is required")
    private String reason;

    @JsonIgnore
    private String comment;

    @Email(message = "Invalid Email")
    @NotNull(message = "is required")
    private String email;

    @JsonIgnore
    private Date timestamp;

//    @Transient
//    @JsonIgnore
//    private Long client;

    @Transient
    @JsonIgnore
    private CustomerState customerState;

    @JsonIgnore
    public Map<String, Object> getAttributeMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", firstName);
        map.put("middleName", middleName);
        map.put("lastName", lastName);
        map.put("address1", address1);
        map.put("address2", address2);
        map.put("regionstate", region_state);
        map.put("city", city);
        map.put("zip", zip);
        map.put("country", country);
        map.put("email", email);
        return map;
    }

    @JsonIgnore
    public Map<String, Object> getNameMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", firstName);
        map.put("middleName", middleName);
        map.put("lastName", lastName);
        return map;
    }

    @JsonIgnore
    public Map<String, Object> getAddressMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("address1", address1);
        map.put("address2", address2);
        map.put("regionstate", region_state);
        map.put("city", city);
        map.put("zip", zip);
        map.put("country", country);
        return map;
    }

    @JsonIgnore
    public Map<String, Object> getOthersMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        return map;
    }
}
