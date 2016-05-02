package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 29, 2016.
 */
public class Address {

    private int id;

    private int userId;

    private String firstname;

    private String lastname;

    private long phone;

    private int countryId;

    private int cityId;

    private String addressLine1;

    private String addressLine2;

    private String region;

    private String postalCode;

    private boolean isPrimary;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

    public int getId() {
        return id;
    }

    public Address setId(int id) {
        this.id = id;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public Address setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public Address setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public long getPhone() {
        return phone;
    }

    public Address setPhone(long phone) {
        this.phone = phone;
        return this;
    }

    public int getCountryId() {
        return countryId;
    }

    public Address setCountryId(int countryId) {
        this.countryId = countryId;
        return this;
    }

    public int getCityId() {
        return cityId;
    }

    public Address setCityId(int cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public Address setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public Address setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public Address setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Address setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public Address setPrimary(boolean primary) {
        isPrimary = primary;
        return this;
    }

    public Timestamp getInsertDate() {
        return insertDate;
    }

    public Address setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public Address setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

}
