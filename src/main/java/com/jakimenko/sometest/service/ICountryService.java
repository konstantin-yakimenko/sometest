package com.jakimenko.sometest.service;

import com.jakimenko.sometest.model.Country;

import java.util.List;

public interface ICountryService {

    List<Country> getCountries();

    Country getById(Integer id);

    Country create(Country country);
}
