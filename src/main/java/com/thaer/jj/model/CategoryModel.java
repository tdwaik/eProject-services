package com.thaer.jj.model;

import com.thaer.jj.entities.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    private String getWhere(Integer id, Boolean isMain, Integer subOf) {
        String where = "";

        if(id != null) {
            where += "c.id = " + id;
        }

        if(isMain != null) {
            where += "c.is_main = " + (isMain? "'true'" : "0");
        }

        if(subOf != null) {
            where += "c.subOf = " + subOf;
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
            category.setIsMain(resultSet.getBoolean("c.isMain"));

            categories.add(category);
        }

        return categories;
    }
}
