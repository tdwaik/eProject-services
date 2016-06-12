package com.thaer.jj.model;

import com.thaer.jj.core.lib.Validator;
import com.thaer.jj.entities.Address;
import com.thaer.jj.entities.Buyer;
import com.thaer.jj.exceptions.UnAuthorizedException;
import com.thaer.jj.model.responseData.AddressResponse;

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
        ResultSet resultSet = executeQuery("SELECT * FROM addresses ad WHERE id = " + id);
        return resultSet.next()? fillData(resultSet) : null;
    }

    public ArrayList<AddressResponse> getAddressesByBuyerId(int buyerId) throws SQLException {
        ResultSet resultSet = executeQuery(
                "SELECT * FROM addresses ad " +
                        "INNER JOIN countries co ON ad.country_id = co.id " +
                        "INNER JOIN cities ci ON ad.city_id = ci.id WHERE ad.user_id = " + buyerId);
        return fillResponseData(resultSet);
    }

    public int addAddress(Buyer buyer, Address address) throws SQLException {

        if(!validateAddAddress(address)) {
            return 0;
        }

        try {
            dbCconnection.setAutoCommit(false);

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
            preparedStatement.setInt(11, buyer.getId());

            if(address.isPrimary() == true) {
                // mark all user address as not primary
                removePrimary(buyer.getId());

                // mark new address as primary
                preparedStatement.setBoolean(10, true);
            }else {
                preparedStatement.setBoolean(10, false);
            }

            preparedStatement.executeUpdate();

            dbCconnection.commit();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                return resultSet.getInt(1);
            }else {
                return 0;
            }

        } catch (SQLException e) {
            dbCconnection.rollback();
            dbCconnection.setAutoCommit(true);
            throw e;
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

    public Address fillData(ResultSet resultSet) throws SQLException {
        Address address = new Address();
        address.setId(resultSet.getInt("ad.id"));
        address.setUserId(resultSet.getInt("ad.user_id"));
        address.setFirstname(resultSet.getString("ad.firstname"));
        address.setLastname(resultSet.getString("ad.lastname"));
        address.setPhone(resultSet.getLong("ad.phone"));
        address.setCountryId(resultSet.getInt("ad.country_id"));
        address.setCityId(resultSet.getInt("ad.city_id"));
        address.setAddressLine1(resultSet.getNString("ad.address_line_1"));
        address.setAddressLine2(resultSet.getNString("ad.address_line_2"));
        address.setRegion(resultSet.getString("ad.region"));
        address.setPostalCode(resultSet.getString("ad.postal_code"));
        address.setPrimary(resultSet.getBoolean("ad.is_primary"));

        return address;
    }

    public ArrayList<AddressResponse> fillResponseData(ResultSet resultSet) throws SQLException {
        ArrayList<AddressResponse> addressesResponse = new ArrayList<>();
        AddressResponse address;
        while(resultSet.next()) {
            address = new AddressResponse();
            address.setId(resultSet.getInt("ad.id"));
            address.setUserId(resultSet.getInt("ad.user_id"));
            address.setFirstname(resultSet.getString("ad.firstname"));
            address.setLastname(resultSet.getString("ad.lastname"));
            address.setPhone(resultSet.getLong("ad.phone"));
            address.setCountryId(resultSet.getInt("ad.country_id"));
            address.setCityId(resultSet.getInt("ad.city_id"));
            address.setAddressLine1(resultSet.getNString("ad.address_line_1"));
            address.setAddressLine2(resultSet.getNString("ad.address_line_2"));
            address.setRegion(resultSet.getString("ad.region"));
            address.setPostalCode(resultSet.getString("ad.postal_code"));
            address.setPrimary(resultSet.getBoolean("ad.is_primary"));

            address.country.setName(resultSet.getString("co.name"));
            address.city.setName(resultSet.getString("ci.name"));

            addressesResponse.add(address);
        }

        return addressesResponse;
    }

    private int removePrimary(int userId) throws SQLException {
        String query = "UPDATE addresses SET is_primary = 0 WHERE user_id = ?";
        PreparedStatement preparedStatement = dbCconnection.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        return preparedStatement.executeUpdate();
    }

    public int makeAddressPrimary(int userId, int addressId) throws SQLException, UnAuthorizedException {

        ResultSet resultSet = executeQuery("SELECT user_id FROM addresses ad WHERE id = " + addressId);

        if(!resultSet.next()) {
            return 0;
        }

        if(resultSet.getInt("user_id") != userId) {
            throw new UnAuthorizedException();
        }

        try {
            dbCconnection.setAutoCommit(false);

            // mark all user address as not primary
            removePrimary(userId);

            String query = "UPDATE addresses SET is_primary = 1 WHERE id = ?";
            PreparedStatement preparedStatement = dbCconnection.prepareStatement(query);
            preparedStatement.setInt(1, addressId);
            int result = preparedStatement.executeUpdate();

            dbCconnection.commit();

            return result;

        } catch (SQLException e) {
            dbCconnection.rollback();
            dbCconnection.setAutoCommit(true);
            throw e;
        }
    }

}
