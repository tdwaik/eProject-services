package com.thaer.jj.model;

import com.thaer.jj.model.sets.ItemAttributesDetails;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Feb 28, 2016.
 */
public class ItemAttributeModel extends AbstractModel {

    public ItemAttributeModel() throws SQLException, ClassNotFoundException, IOException {
    }

    public ArrayList<ItemAttributesDetails> getItemAttributes(int itemId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = executeQuery("SELECT * FROM items_attributes_values iav INNER JOIN items_attributes ia ON iav.attribute_id = ia.id  WHERE iav.item_id = " + itemId);

        ArrayList<ItemAttributesDetails> itemAttributesList = new ArrayList<>();

        while (resultSet.next()) {
            ItemAttributesDetails itemAttributesDetails = new ItemAttributesDetails();

            itemAttributesDetails.itemAttribute.setId(resultSet.getInt("ia.id"));
            itemAttributesDetails.itemAttribute.setName(resultSet.getString("ia.name"));

            itemAttributesDetails.itemAttributeValue.setId(resultSet.getInt("iav.id"));
            itemAttributesDetails.itemAttributeValue.setAttributeId(resultSet.getInt("ia.id"));
            itemAttributesDetails.itemAttributeValue.setValue(resultSet.getString("iav.value"));
        }

        return itemAttributesList;
    }

    public int addItemAttribute(String name) throws SQLException {
        if(name == null) {
            throw new IllegalArgumentException();
        }

        return executeUpdate("INSERT INTO items_attributes (name) VALUES ('" + name + "')");
    }

    public void addItemAttributesValues() {

    }

}
