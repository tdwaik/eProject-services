package com.thaer.jj.controller.backoffice;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Thaer AlDwaik <thaer_aldwaik@hotmail.com>
 * @since Apr 15, 2016.
 */
@Path("backoffice/categories")
public class CategoriesController extends BackOfficeController {

    @PUT @Path("/")
    public Response addCategory(@FormParam("subOf") int subOf, @FormParam("name") String name) {
        return Response.status(201).build();
    }

    @POST @Path("/edit")
    public Response editCategory(@FormParam("id") int id, @FormParam("name") String name) {
        return Response.status(202).build();
    }

}
