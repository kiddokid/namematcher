package com.example.filterproject.namematcher.rest;

import com.example.filterproject.namematcher.model.RiskCustomer;
import com.example.filterproject.namematcher.model.descision.ServiceDescision;
import com.example.filterproject.namematcher.service.CustomerService;
import com.example.filterproject.namematcher.service.NameMatcherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/nm")
@Slf4j
public class PrivateRestController {

    private final NameMatcherService matcherService;
    private final CustomerService customerService;

    public PrivateRestController(NameMatcherService matcherService,
                                 CustomerService customerService) {
        this.matcherService = matcherService;
        this.customerService = customerService;
    }

    @Validated
    @GetMapping("/match")
    public ResponseEntity matchCustomer(@RequestParam("firstName") String firstName, @RequestParam("middleName") Optional<String> middleName,
                                        @RequestParam("lastName") String lastName, @RequestParam("email") String email,
                                        @RequestParam("address1") String address1, @RequestParam("address2") Optional<String> address2,
                                        @RequestParam("region_state") String region_state, @RequestParam("city") String city,
                                        @RequestParam("zip") String zip, @RequestParam("country") String country) {
        RiskCustomer riskCustomer = RiskCustomer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName.orElse(null))
                .email(email)
                .address1(address1)
                .address2(address2.orElse(null))
                .region_state(region_state)
                .city(city)
                .zip(zip)
                .country(country)
                .build();
        ServiceDescision serviceDescision = matcherService.process(riskCustomer);
        return new ResponseEntity(serviceDescision, HttpStatus.OK);
    }

    @Validated //TODO check this annotation
    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody RiskCustomer riskCustomer) {
        customerService.save(riskCustomer);
        return new ResponseEntity(HttpStatus.OK);
    }
}
