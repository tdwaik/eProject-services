package com.thaer.jj.model.responseData;

import com.thaer.jj.entities.Address;
import com.thaer.jj.entities.City;
import com.thaer.jj.entities.Country;

/**
 * Created by stig on 6/11/16.
 */
public class AddressResponse extends Address {

    public Country country;

    public City city;

    public AddressResponse() {
        country = new Country();
        city = new City();
    }
}
