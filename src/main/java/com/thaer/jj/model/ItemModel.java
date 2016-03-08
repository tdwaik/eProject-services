package com.thaer.jj.model;

import com.thaer.jj.entities.Item;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 08, 2016.
 */
public class ItemModel extends AbstractModel {
    public ItemModel() throws SQLException {
    }

    public int addItem(Item item) throws SQLException {
        return executeUpdate(
                "INSERT INTO items " +
                        "(category_id, title, description, picture, status) " +
                        "VALUES " +
                        "(" + item.getCategoryId() + ", " +
                        "'" + item.getTitle() + "', " +
                        "'" + item.getDescription() + "', " +
                        "'" + item.getPicture() + "', " +
                        "'pending')"
        );
    }
}
