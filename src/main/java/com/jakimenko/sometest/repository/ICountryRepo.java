package com.jakimenko.sometest.repository;

import com.jakimenko.sometest.model.Country;

import java.util.List;

public interface ICountryRepo {

    List<Country> getCountries();

    Country getById(Integer id);

    Country getByName(String name);

    Country save(Country country);
}
