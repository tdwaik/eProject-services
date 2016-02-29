package com.thaer.jj.model.helpers;

import com.thaer.jj.entities.Category;
import com.thaer.jj.entities.Item;
import com.thaer.jj.entities.Offer;

import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016
 */
public class ProductDetails {

    public Offer offer;

    public Item item;

    public Category category;

    public ArrayList<ItemAttributesDetails> itemAttributesDetails;

    public ProductDetails() {
        offer       = new Offer();
        item        = new Item();
        category    = new Category();
    }

}
