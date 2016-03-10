package com.thaer.jj.model.sets;

import com.thaer.jj.entities.Category;
import com.thaer.jj.entities.Offer;
import com.thaer.jj.entities.OfferOption;

import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016
 */
public class ProductDetails {

    public OfferOption offerOption;

    public Offer offer;

    public Category category;

    public ArrayList<ItemAttributesDetails> itemAttributesDetails;

    public ProductDetails() {
        offerOption = new OfferOption();
        offer = new Offer();
        category    = new Category();
        itemAttributesDetails = new ArrayList<>();
    }

}
