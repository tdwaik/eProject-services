package com.thaer.jj.model;

import com.thaer.jj.entities.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 11, 2016.
 */
public class CategoryModel extends AbstractModel {
    public CategoryModel() throws SQLException {
    }

    public Category getCategoryById(int id) throws SQLException {
        ArrayList<Category> categories = getCategoriesList(id, null, null);
        if(categories.isEmpty()) {
            return null;
        }

        return categories.get(0);
    }

    private ArrayList<Category> getCategoriesList(Integer id, Boolean isMain, Integer subOf) throws SQLException {
        String where = getWhere(null, isMain, subOf);
        ResultSet resultSet = executeQuery("SELECT * FROM categories c " + where);
        return fillData(resultSet);
    }

    public ArrayList<Category> getMainCategoriesList() throws SQLException {
        return getCategoriesList(null, true, null);
    }

    public ArrayList<Category> getSubCategoriesList(int subOf) throws SQLException {
        return getCategoriesList(null, false, subOf);
    }

    public int addCategory(Category category) throws SQLException, IllegalArgumentException {

        if(category.getName().length() < 2) {
            throw new IllegalArgumentException();
        }


        PreparedStatement preparedStatement = dbCconnection.prepareStatement("INSERT INTO categories (`is_main`, `sub_of`, `name`) VALUES (? , ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setBoolean(1, category.getIsMain());
        preparedStatement.setInt(2, category.getSubOf());
        preparedStatement.setString(3, category.getName());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }else {
            throw new IllegalArgumentException();
        }
    }

    private String getWhere(Integer id, Boolean isMain, Integer subOf) {
        String where = "";

        if(id != null) {
            where += " c.id = " + id;
        }

        if(isMain != null) {
            where += (where.length() > 1? " AND" : "") + " c.is_main = " + (isMain? "1" : "0");
        }

        if(subOf != null) {
            where += (where.length() > 1? " AND" : "") + " c.sub_of = " + subOf;
        }

        return where.length() > 1? "WHERE " + where : where;
    }

    private ArrayList<Category> fillData(ResultSet resultSet) throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();
        Category category;

        while (resultSet.next()) {
            category = new Category();
            category.setId(resultSet.getInt("c.id"));
            category.setSubOf(resultSet.getInt("c.sub_of"));
            category.setName(resultSet.getString("c.name"));
            category.setIsMain(resultSet.getBoolean("c.is_main"));

            categories.add(category);
        }

        return categories;
    }
}
