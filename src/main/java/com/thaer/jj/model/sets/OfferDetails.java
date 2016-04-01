package com.thaer.jj.model.sets;

import com.thaer.jj.entities.Brand;
import com.thaer.jj.entities.Category;
import com.thaer.jj.entities.Offer;

import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016
 */
public class OfferDetails {

    public Offer offer;

    public ArrayList<OfferOptionDetail> offerOptionDetails;

    public Category category;

    private Brand brand;

    public OfferDetails() {
        offer               = new Offer();
        offerOptionDetails  = new ArrayList<>();
        category            = new Category();
        brand               = new Brand();
    }

    public OfferDetails setNewOfferId(int offerId) {
        for(OfferOptionDetail offerOptionDetail : offerOptionDetails) {
            offerOptionDetail.offerOption.setOfferId(offerId);
        }

        return this;
    }

}
