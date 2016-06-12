package com.thaer.jj.model;

import com.thaer.jj.entities.City;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since May 1, 2016.
 */
public class CityModel extends AbstractModel {
    public CityModel() throws SQLException {
    }

    public ArrayList<City> getCitiesListByCountryId(int countryId) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT id, name FROM cities WHERE country_id = " + countryId + " ORDER BY name ASC");

        ArrayList<City> cities = new ArrayList<>();
        City city;
        while(resultSet.next()) {
            city = new City();
            city.setId(resultSet.getInt("id"));
            city.setName(resultSet.getString("name"));
            city.setCountryId(countryId);

            cities.add(city);
        }

        return cities;
    }

    public boolean isCityExists(int countryId, int cityId) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT count(*) as count from cities WHERE id = " + cityId + " AND country_id = " + countryId);
        return resultSet.next() && resultSet.getInt("count") == 1;
    }
}
