package com.example.filterproject.namematcher.rest;

import com.example.filterproject.namematcher.model.RiskCustomer;
import com.example.filterproject.namematcher.model.descision.ServiceDescision;
import com.example.filterproject.namematcher.service.CustomerService;
import com.example.filterproject.namematcher.service.NameMatcherService;
import com.google.gson.Gson;
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

    private static final Gson gson = new Gson();

    private final NameMatcherService matcherService;
    private final CustomerService customerService;

    public PrivateRestController(NameMatcherService matcherService,
                                 CustomerService customerService) {
        this.matcherService = matcherService;
        this.customerService = customerService;
    }

    @PostMapping("/match")
    public ResponseEntity<ServiceDescision> matchCustomerPost(@RequestBody RiskCustomer riskCustomer) {
        ServiceDescision serviceDescision = matcherService.process(riskCustomer);
        return new ResponseEntity<>(serviceDescision, HttpStatus.OK);
    }

    @Validated //TODO check this annotation
    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody RiskCustomer riskCustomer) {
        customerService.save(riskCustomer);
        return new ResponseEntity(HttpStatus.OK);
    }
}
