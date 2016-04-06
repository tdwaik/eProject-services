package com.thaer.jj.model.sets;

import com.thaer.jj.entities.Brand;
import com.thaer.jj.entities.Offer;

import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since February 28, 2016
 */
public class OfferDetails {

    public Offer offer;

    public ArrayList<VariationDetails> variationsDetails;

    private Brand brand;

    public OfferDetails() {
        offer               = new Offer();
        variationsDetails  = new ArrayList<>();
        brand               = new Brand();
    }

    public OfferDetails setNewOfferId(int offerId) {
        for(VariationDetails variationDetails : variationsDetails) {
            variationDetails.offerVariation.setOfferId(offerId);
        }

        return this;
    }

}
