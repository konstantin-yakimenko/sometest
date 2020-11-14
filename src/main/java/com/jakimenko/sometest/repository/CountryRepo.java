package com.jakimenko.sometest.repository;

import com.jakimenko.sometest.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CountryRepo implements ICountryRepo {

    private final NamedParameterJdbcTemplate npjt;

    @Autowired
    public CountryRepo(DataSource dataSource) {
        this.npjt = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Country> getCountries() {
        return npjt.query("select id, name from country",
                new MapSqlParameterSource(),
                (rs, rowNum) -> new Country(rs.getInt("id"), rs.getString("name")));
    }

    @Override
    public Country getById(Integer id) {
        return npjt.queryForObject(
                "select id, name from country where id = :id",
                new MapSqlParameterSource("id", id),
                (rs, rowNum) -> new Country(rs.getInt("id"), rs.getString("name"))
        );
    }

    @Override
    public Country getByName(String name) {
        return npjt.queryForObject(
                "select id, name from country where name = :name",
                new MapSqlParameterSource("name", name),
                (rs, rowNum) -> new Country(rs.getInt("id"), rs.getString("name"))
        );
    }

    @Override
    public Country save(Country country) {
        if (country.getId() == null) {
            npjt.update("insert into country(name) values(:name)",
                    new MapSqlParameterSource("name", country.getName()));
        } else {
            npjt.update("insert into country(id, name) values(:id, :name)",
                    new MapSqlParameterSource()
                            .addValue("id", country.getId())
                            .addValue("name", country.getName()));
        }
        return getByName(country.getName());
    }
}
