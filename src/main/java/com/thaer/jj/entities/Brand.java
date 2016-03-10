package com.thaer.jj.entities;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 10, 2016.
 */
public class Brand {

    private int id;

    private int seller_id;

    private String name;

    private String brand;

    public int getId() {
        return id;
    }

    public Brand setId(int id) {
        this.id = id;
        return this;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public Brand setSeller_id(int seller_id) {
        this.seller_id = seller_id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Brand setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public Brand setBrand(String brand) {
        this.brand = brand;
        return this;
    }
}
