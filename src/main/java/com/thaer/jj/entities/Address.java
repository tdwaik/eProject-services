package com.thaer.jj.entities;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by stig on 4/29/16.
 */
public class Address {

    private int id;

    private int userId;

    private String firstname;

    private String lastname;

    private BigInteger phone;

    private int countryId;

    private int cityId;

    private String region;

    private String street;

    private String buildingNumber;

    private int floorNumber;

    private int apartmentNumber;

    private String landmark;

    private String note;

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

    public BigInteger getPhone() {
        return phone;
    }

    public Address setPhone(BigInteger phone) {
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

    public String getRegion() {
        return region;
    }

    public Address setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public Address setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
        return this;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public Address setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
        return this;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public Address setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
        return this;
    }

    public String getLandmark() {
        return landmark;
    }

    public Address setLandmark(String landmark) {
        this.landmark = landmark;
        return this;
    }

    public String getNote() {
        return note;
    }

    public Address setNote(String note) {
        this.note = note;
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
