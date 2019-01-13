package com.example.filterproject.namematcher.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(schema = "namematching")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormilizedCustomerData implements Customer{

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

    @JsonIgnore
    public Map<String, Object> getAttributeMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("address1", address1);
        map.put("address2", address2);
        map.put("region", region);
        map.put("city", city);
        map.put("zip", zip);
        map.put("country", country);
        return map;
    }

    @JsonIgnore
    public Map<String, Object> getNameMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

    @JsonIgnore
    public Map<String, Object> getAddressMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("address1", address1);
        map.put("address2", address2);
        map.put("region", region);
        map.put("city", city);
        map.put("zip", zip);
        map.put("country", country);
        return map;
    }

    @JsonIgnore
    public Map<String, Object> getOthersMap() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
