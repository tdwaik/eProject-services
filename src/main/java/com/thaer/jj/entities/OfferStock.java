package com.thaer.jj.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 10, 2016.
 */
public class OfferStock {

    public static String tableName = "offers_stock";

    private int id;

    private int variationId;

    private String sku;

    private int sizeId;

    private BigDecimal price;

    private int stockQuantity;

    private Timestamp insertDate;

    private Timestamp lastUpdate;

    public int getId() {
        return id;
    }

    public OfferStock setId(int id) {
        this.id = id;
        return this;
    }

    public int getVariationId() {
        return variationId;
    }

    public OfferStock setVariationId(int variationId) {
        this.variationId = variationId;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public OfferStock setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public int getSizeId() {
        return sizeId;
    }

    public OfferStock setSizeId(int sizeId) {
        this.sizeId = sizeId;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferStock setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public OfferStock setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
        return this;
    }

    public Timestamp getInsertDate() {
        return insertDate;
    }

    public OfferStock setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public OfferStock setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

}
