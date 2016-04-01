package com.thaer.jj.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 10, 2016.
 */
public class OfferStockDetail {

    public static String tableName = "offers_stock_details";

    private int id;

    private int offerOptionId;

    private int sizeId;

    private BigDecimal price;

    private int stockQuantity;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

    public int getId() {
        return id;
    }

    public OfferStockDetail setId(int id) {
        this.id = id;
        return this;
    }

    public int getOfferOptionId() {
        return offerOptionId;
    }

    public OfferStockDetail setOfferOptionId(int offerOptionId) {
        this.offerOptionId = offerOptionId;
        return this;
    }

    public int getSizeId() {
        return sizeId;
    }

    public OfferStockDetail setSizeId(int sizeId) {
        this.sizeId = sizeId;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferStockDetail setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public OfferStockDetail setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
        return this;
    }
}
