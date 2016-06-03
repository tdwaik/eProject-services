package com.thaer.jj.model;

import com.thaer.jj.core.lib.Validator;
import com.thaer.jj.entities.Address;
import com.thaer.jj.entities.Buyer;
import com.thaer.jj.exceptions.UnAuthorizedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by stig on 5/1/16.
 */
public class AddressModel extends AbstractModel {
    public AddressModel() throws SQLException {
    }

    public Address getAddressById(int id) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT * FROM addresses WHERE id = " + id);
        ArrayList<Address> addresses = fillData(resultSet);
        return addresses.size() > 0? addresses.get(0) : null;
    }

    public ArrayList<Address> getAddressesByBuyerId(int buyerId) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT * FROM addresses WHERE user_id = " + buyerId);
        return fillData(resultSet);
    }

    public int addAddress(Buyer buyer, Address address) throws SQLException {

        if(!validateAddAddress(address)) {
            return 0;
        }

        String query = "INSERT INTO addresses (firstname, lastname, phone, country_id, city_id, address_line_1, address_line_2, region, postal_code, is_primary, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, address.getFirstname());
        preparedStatement.setString(2, address.getLastname());
        preparedStatement.setLong(3, address.getPhone());
        preparedStatement.setInt(4, address.getCountryId());
        preparedStatement.setInt(5, address.getCityId());
        preparedStatement.setString(6, address.getAddressLine1());
        preparedStatement.setString(7, address.getAddressLine2());
        preparedStatement.setString(8, address.getRegion());
        preparedStatement.setString(9, address.getPostalCode());
        preparedStatement.setBoolean(10, address.isPrimary());
        preparedStatement.setInt(11, buyer.getId());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }else {
            return 0;
        }
    }

    public int deleteAddressById(Buyer buyer, int addressId) throws SQLException, UnAuthorizedException {
        Address address = getAddressById(addressId);
        if(address == null || address.getUserId() != buyer.getId()) {
            throw new UnAuthorizedException();
        }

        String query = "DELETE FROM addresses WHERE id = " + addressId;
        PreparedStatement preparedStatement = dbCconnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        return preparedStatement.executeUpdate();
    }

    public boolean validateAddAddress(Address address) throws SQLException {

        ArrayList<String> errors = new ArrayList<>();

        if(address.getFirstname() == null || address.getFirstname().length() < 2) {
            errors.add("firstname");
        }
        if(address.getLastname() == null || address.getLastname().length() < 2) {
            errors.add("lastname");
        }

        if(!Validator.checkPhoneNumber(address.getPhone())) {
            errors.add("phone");
        }

        CountryModel countryModel = new CountryModel();
        if(address.getCountryId() < 1 || !countryModel.isCountryExists(address.getCountryId())) {
            errors.add("country");
        }

        CityModel cityModel = new CityModel();
        if(address.getCityId() < 1 || !cityModel.isCityExists(address.getCountryId(), address.getCityId())) {
            errors.add("city");
        }

        return errors.size() == 0;
    }

    public ArrayList<Address> fillData(ResultSet resultSet) throws SQLException {
        ArrayList<Address> addresses = new ArrayList<>();
        Address address;
        while(resultSet.next()) {
            address = new Address();
            address.setId(resultSet.getInt("id"));
            address.setUserId(resultSet.getInt("user_id"));
            address.setFirstname(resultSet.getString("firstname"));
            address.setLastname(resultSet.getString("lastname"));
            address.setPhone(resultSet.getLong("phone"));
            address.setCountryId(resultSet.getInt("country_id"));
            address.setCityId(resultSet.getInt("city_id"));
            address.setAddressLine1(resultSet.getNString("address_line_1"));
            address.setAddressLine2(resultSet.getNString("address_line_2"));
            address.setRegion(resultSet.getString("region"));
            address.setPostalCode(resultSet.getString("postal_code"));
            address.setPrimary(resultSet.getBoolean("is_primary"));

            addresses.add(address);
        }

        return addresses;
    }

}
