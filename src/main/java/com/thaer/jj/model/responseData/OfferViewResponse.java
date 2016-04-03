package com.thaer.jj.model.responseData;

import com.thaer.jj.entities.OfferStockDetail;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 03, 2016.
 */
public class OfferViewResponse {

    public int optionId;

    public String brand;

    public String title;

    public String description;

    public String picture;

    public String color;

    public ArrayList<OfferStockDetail> offerStockDetails;

    public HashMap<Integer, String> variationsPictures;

    public HashMap<Integer, String> variationsColors;

}
