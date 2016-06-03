package com.thaer.jj.entities;

import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since June 3, 2016.
 */
public class Cart {

    private int id;

    private int buyerId;

    private int offerStockId;

    private int quantity;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

    public int getId() {
        return id;
    }

    public Cart setId(int id) {
        this.id = id;
        return this;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public Cart setBuyerId(int buyerId) {
        this.buyerId = buyerId;
        return this;
    }

    public int getOfferStockId() {
        return offerStockId;
    }

    public Cart setOfferStockId(int offerStockId) {
        this.offerStockId = offerStockId;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Cart setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Timestamp getInsertDate() {
        return insertDate;
    }

    public Cart setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public Cart setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

}
