package com.thaer.jj.model.responseData;

import java.math.BigDecimal;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since June 11, 2016.
 */
public class CartPriceSummary {

    public BigDecimal shippingCost;

    public BigDecimal itemsTotalPrice;

    public BigDecimal subtotal;

    public CartPriceSummary() {
        itemsTotalPrice = new BigDecimal(0);
        shippingCost = new BigDecimal(0);
        subtotal = new BigDecimal(0);
    }

}
