package com.thaer.jj.model.sets;

import com.thaer.jj.entities.*;

import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016
 */
public class OfferDetails {

    public Offer offer;

    public OfferOption offerOption;

    public Category category;

    public OfferPrice offerPrice;

    public ArrayList<OfferReview> offerReviews;

    public OfferDetails() {
        offer           = new Offer();
        offerOption     = new OfferOption();
        category        = new Category();
        offerPrice      = new OfferPrice();
        offerReviews    = new ArrayList<>();
    }

}
