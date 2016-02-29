package com.thaer.jj.model.helper;

import com.thaer.jj.entities.Category;
import com.thaer.jj.entities.Item;
import com.thaer.jj.entities.Offer;

import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016
 */
public class Product {

    public Offer offer;

    public Item item;

    public Category category;

    public ArrayList<ItemAttributesDetails> itemAttributesDetails;

    public Product() {
        offer       = new Offer();
        item        = new Item();
        category    = new Category();
    }

}
