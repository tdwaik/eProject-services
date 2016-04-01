package com.thaer.jj.model.sets;

import com.thaer.jj.entities.OfferOption;
import com.thaer.jj.entities.OfferStockDetail;

import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 01, 2016.
 */
public class OfferOptionDetail {

    public OfferOption offerOption;

    public ArrayList<OfferStockDetail> offerStockDetails;

    public OfferOptionDetail() {
        offerOption = new OfferOption();
        offerStockDetails = new ArrayList<>();
    }

    public OfferOptionDetail setNewOfferOptionId(int offerOptionId) {
        for(OfferStockDetail offerStockDetail : offerStockDetails) {
            offerStockDetail.setStockQuantity(offerOptionId);
        }

        return this;
    }

}
