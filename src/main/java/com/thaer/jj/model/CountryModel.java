package com.thaer.jj.model;

import com.thaer.jj.entities.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since April 30, 2016.
 */
public class CountryModel extends AbstractModel {
    public CountryModel() throws SQLException {
    }

    public ArrayList<Country> getCountriesList() throws SQLException {

        ResultSet resultSet = executeQuery("SELECT * from countries ORDER BY name ASC");

        ArrayList<Country> countries = new ArrayList<>();
        Country country;
        while(resultSet.next()) {
            country = new Country();
            country.setId(resultSet.getInt("id"));
            country.setName(resultSet.getString("name"));
            countries.add(country);
        }

        return countries;
    }

    public boolean isCountryExists(int countryId) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT count(*) as count from countries WHERE id = " + countryId);
        return resultSet.next() && resultSet.getInt("count") == 1;
    }
}
