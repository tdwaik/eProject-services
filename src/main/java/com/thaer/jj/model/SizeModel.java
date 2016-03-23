package com.thaer.jj.model;

import com.thaer.jj.entities.Size;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 23, 2016.
 */
public class SizeModel extends AbstractModel {
    public SizeModel() throws SQLException {
    }

    public ArrayList<Size> getSizesByCategoryId(int categoryId) throws SQLException {
        ResultSet resultSet = executeQuery("SELECT * FROM sizes WHERE category_id = " + categoryId);
        return fillData(resultSet);
    }

    private ArrayList<Size> fillData(ResultSet resultSet) throws SQLException {
        ArrayList<Size> sizes = new ArrayList<>();

        while(resultSet.next()) {
            Size size = new Size();
            size.setId(resultSet.getInt("id"));
            size.setCategoryId(resultSet.getInt("category_id"));
            size.setName(resultSet.getString("name"));
            //size.setInsertDate(resultSet.getTimestamp("insert_date"));
            //size.setLastUpdate(resultSet.getTimestamp("last_update"));

            sizes.add(size);
        }

        return sizes;
    }
}
