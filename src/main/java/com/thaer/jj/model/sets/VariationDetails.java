package com.thaer.jj.model.sets;

import com.thaer.jj.entities.OfferStock;
import com.thaer.jj.entities.OfferVariation;

import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 01, 2016.
 */
public class VariationDetails {

    public OfferVariation offerVariation;

    public ArrayList<OfferStock> offerStockList;

    public VariationDetails() {
        offerVariation = new OfferVariation();
        offerStockList = new ArrayList<>();
    }

    public VariationDetails setNewVariationId(int offerOptionId) {
        for(OfferStock offerStock : offerStockList) {
            offerStock.setVariationId(offerOptionId);
        }

        return this;
    }

}
