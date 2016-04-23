package com.thaer.jj.controller.backoffice;

import com.thaer.jj.entities.Category;
import com.thaer.jj.model.CategoryModel;

import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since April 23, 2016.
 */
public class CategoryController extends BackOfficeController {

    @PUT
    @Path("/")
    public Response addCategory(@FormParam("name") String name, @FormParam("isMain") boolean isMain, @FormParam("subOf") int subOf) {
        try {

            if(getAuthBackofficeUser() == null) {
                return Response.status(401).build();
            }

            Category category = new Category();
            category.setName(name);
            category.setIsMain(isMain);
            category.setSubOf(subOf);

            CategoryModel categoryModel = new CategoryModel();
            if(categoryModel.addCategory(category) > 0) {
                return Response.status(201).build();
            }else {
                return Response.status(400).build();
            }

        }catch (IllegalArgumentException e) {
            return Response.status(400).build();
        }catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

}
