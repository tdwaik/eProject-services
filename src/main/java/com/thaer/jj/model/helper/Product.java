package com.thaer.jj.model.helper;

import com.thaer.jj.entities.Category;
import com.thaer.jj.entities.Item;
import com.thaer.jj.entities.Offer;

/**
 * Created by Thaer Aldwaik on February 28, 2016
 */
public class Product {

    public Offer offer;

    public Item item;

    public Category category;

    public Product() {
        offer       = new Offer();
        item        = new Item();
        category    = new Category();
    }

}
