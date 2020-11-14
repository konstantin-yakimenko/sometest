package com.jakimenko.sometest.service;

import com.jakimenko.sometest.model.Country;
import com.jakimenko.sometest.repository.ICountryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService implements ICountryService {

    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);

    private final ICountryRepo countryRepo;

    @Autowired
    public CountryService(ICountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    @Override
    public List<Country> getCountries() {
        List<Country> countries = countryRepo.getCountries();
        logger.info("Answer for getCountries request: {}", countries);
        return countries;
    }

    @Override
    public Country getById(Integer id) {
        Country country = countryRepo.getById(id);
        logger.info("Answer for getCountry by {} request: {}", id, country);
        return country;
    }

    @Override
    public Country create(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("Не указана страна при создании");
        }
        return countryRepo.save(country);
    }
}
