package com.thaer.jj.model;

import com.thaer.jj.entities.Size;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public int addSize(Size size) throws SQLException, IllegalArgumentException {

        if(size.getName().length() < 1) {
            throw new IllegalArgumentException();
        }

        PreparedStatement preparedStatement = dbCconnection.prepareStatement("INSERT INTO sizes (`category_id`, `name`) VALUES (? , ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, size.getCategoryId());
        preparedStatement.setString(2, size.getName());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }else {
            throw new IllegalArgumentException();
        }
    }

    public boolean updateSize(Size size) throws SQLException, IllegalArgumentException {
        if(size.getId() < 1 || size.getName().length() < 1) {
            throw new IllegalArgumentException();
        }

        PreparedStatement preparedStatement = dbCconnection.prepareStatement("UPDATE sizes SET `name` = ? WHERE `id` = ?", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, size.getName());
        preparedStatement.setInt(2, size.getId());
        return preparedStatement.executeUpdate() == 1;
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
