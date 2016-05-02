package com.thaer.jj.model;

import com.thaer.jj.entities.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by stig on 4/30/16.
 */
public class CountryModel extends AbstractModel {
    public CountryModel() throws SQLException {
    }

    public ArrayList<Country> getCountriesList() throws SQLException {

        ResultSet resultSet = executeQuery("SELECT * from countries");

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
}
