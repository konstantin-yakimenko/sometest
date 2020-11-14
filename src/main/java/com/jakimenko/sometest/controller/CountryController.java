package com.jakimenko.sometest.controller;

import com.jakimenko.sometest.model.Country;
import com.jakimenko.sometest.service.ICountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private final Logger logger = LoggerFactory.getLogger(CountryController.class);

    private final ICountryService countryService;

    @Autowired
    public CountryController(ICountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/")
    public List<Country> getCountries() {
        logger.info("Request for get all countries");
        return countryService.getCountries();
    }

    @GetMapping("/{id}")
    public Country getById(@PathVariable("id") Integer id) {
        logger.info("Request country by id {}", id);
        return countryService.getById(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> createCountry(@RequestBody Country country) {
        logger.info("Create country request: {}", country);
        Country result = countryService.create(country);
        logger.info("Country {} was created", result);
        return ResponseEntity.created(URI.create("")).body(result);
    }
}

