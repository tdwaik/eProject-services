package com.thaer.jj.model.sets;

import com.thaer.jj.entities.OfferVariation;
import com.thaer.jj.entities.OfferStockDetail;

import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 01, 2016.
 */
public class VariationDetails {

    public OfferVariation offerVariation;

    public ArrayList<OfferStockDetail> offerStockDetails;

    public VariationDetails() {
        offerVariation = new OfferVariation();
        offerStockDetails = new ArrayList<>();
    }

    public VariationDetails setNewVariationId(int offerOptionId) {
        for(OfferStockDetail offerStockDetail : offerStockDetails) {
            offerStockDetail.setVariationId(offerOptionId);
        }

        return this;
    }

}
