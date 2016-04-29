package com.thaer.jj.controller.backoffice;

import com.thaer.jj.entities.Category;
import com.thaer.jj.model.CategoryModel;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 15, 2016.
 */
@Path("backoffice/categories")
public class CategoriesController extends BackOfficeController {

    @PUT @Path("/")
    public Response addCategory(@FormParam("subOf") int subOf, @FormParam("name") String name) {
        try {
            CategoryModel categoryModel = new CategoryModel();
            Category category = new Category();
            category.setSubOf(subOf);
            category.setName(name);

            int newCategoryId = categoryModel.addCategory(category);
            category.setId(newCategoryId);

            return Response.status(Response.Status.CREATED).entity(toJson(category)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @POST @Path("/")
    public Response editCategory(@FormParam("id") int id, @FormParam("name") String name) {
        try {
            CategoryModel categoryModel = new CategoryModel();
            Category category = new Category();
            category.setId(id);
            category.setName(name);

            boolean result = categoryModel.updateCategory(category);
            if(result) {
                return Response.status(Response.Status.ACCEPTED).entity(toJson(category)).build();
            }else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
