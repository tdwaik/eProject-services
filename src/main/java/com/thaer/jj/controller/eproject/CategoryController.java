package com.thaer.jj.controller.eproject;

import com.thaer.jj.entities.Category;
import com.thaer.jj.model.CategoryModel;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since March 07, 2016.
 */
@Path("categories")
public class CategoryController extends MainController {

    @GET
    @Path("/main")
    public Response getMainCategories() {
        try {
            CategoryModel categoryModel = new CategoryModel();
            ArrayList<Category> categories = categoryModel.getMainCategoriesList();
            return Response.ok().entity(toJson(categories)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/sub/{subOf}")
    public Response getSubCategories(@PathParam("subOf") int subOf) {
        try {
            CategoryModel categoryModel = new CategoryModel();
            ArrayList<Category> categories = categoryModel.getSubCategoriesList(subOf);
            return Response.ok().entity(toJson(categories)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

}
