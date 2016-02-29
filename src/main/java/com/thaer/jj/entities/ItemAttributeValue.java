package com.thaer.jj.entities;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Feb 28, 2016.
 */
public class ItemAttributeValue {

    private int id;

    private int itemId;

    private int attributeId;

    private String value;

    public int getId() {
        return id;
    }

    public ItemAttributeValue setId(int id) {
        this.id = id;
        return this;
    }

    public int getItemId() {
        return itemId;
    }

    public ItemAttributeValue setItemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public ItemAttributeValue setAttributeId(int attributeId) {
        this.attributeId = attributeId;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ItemAttributeValue setValue(String value) {
        this.value = value;
        return this;
    }
}
