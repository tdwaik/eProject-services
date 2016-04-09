package com.thaer.jj.model;

import com.thaer.jj.entities.Brand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 08, 2016.
 */
public class BrandsModel extends AbstractModel {
    public BrandsModel() throws SQLException {
    }

    public Brand getBrandById(int brandId) throws SQLException, IllegalArgumentException {
        if(brandId < 1) {
            throw new IllegalArgumentException();
        }

        ResultSet resultSet = executeQuery("SELECT id, name FROM brands WHERE id = " + brandId);
        ArrayList<Brand> brands = fillData(resultSet);
        return brands.size() == 1? brands.get(0) : null;
    }

    public ArrayList<Brand> getBrandsBySellerId(int sellerId) throws SQLException, IllegalArgumentException {
        if(sellerId < 1) {
            throw new IllegalArgumentException();
        }

        ResultSet resultSet = executeQuery("SELECT id, name FROM brands WHERE seller_id = " + sellerId + " AND status = 'active'");
        return fillData(resultSet);
    }

    public ArrayList<Brand> fillData(ResultSet resultSet) throws SQLException {
        ArrayList<Brand> brands = new ArrayList<>();
        Brand brand;

        while(resultSet.next()) {
            brand = new Brand();
            brand.setId(resultSet.getInt("id"));
            brand.setName(resultSet.getString("name"));
            brands.add(brand);
        }

        return brands;
    }
}
