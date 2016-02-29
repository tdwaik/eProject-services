package com.thaer.jj.model.helper;

import com.thaer.jj.entities.ItemAttribute;
import com.thaer.jj.entities.ItemAttributeValue;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Feb 28, 2016.
 */
public class ItemAttributesDetails {

    public ItemAttribute itemAttribute;

    public ItemAttributeValue itemAttributeValue;

    public ItemAttributesDetails() {
        itemAttribute = new ItemAttribute();
        itemAttributeValue = new ItemAttributeValue();
    }

}
