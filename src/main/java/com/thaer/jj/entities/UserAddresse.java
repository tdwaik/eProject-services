package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 21, 2016.
 */
public class UserAddresse {

    private int id;

    private int userId;

    private String addressName;

    private String firstname;

    private String lastname;

    private int phone;

    private int countryId;

    private int cityId;

    private int postalCode;

    private String area;

    private String street;

    private int houseNumber;

    private int floorNumber;

    private int apartmentNumber;

    private String note;

    private char preferredDeliveryTime;

    private boolean isPrimary;

    private String nearestLandmark;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

}
